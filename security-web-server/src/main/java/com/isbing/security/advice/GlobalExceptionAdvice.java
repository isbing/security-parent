package com.isbing.security.advice;

import com.isbing.security.common.bean.CommonErrors;
import com.isbing.security.common.bean.ErrorResponse;
import com.isbing.security.common.exception.ServerBizException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by song bing
 * Created time 2019/4/23 19:07
 */
public class GlobalExceptionAdvice extends SimpleMappingExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//如果是rpc的,则把响应设置为500
		if (request.getServletPath().startsWith("/rpc")) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

		//Accept
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			String requestContentType = getRequestContentType(request);
			if (requestContentType != null) {//优先处理request 含有content type
				requestContentType = requestContentType.toLowerCase();
				if (requestContentType.startsWith(MediaType.APPLICATION_JSON_VALUE)) {//json
					return drawJson(request, response, ex);
				}
				// todo 加上下面的注释 无法实现预览的效果
				//				} else if (requestContentType.startsWith(MediaType.TEXT_HTML_VALUE)) {//html
				//					return drawHtml(request, response, ex);
				//				}
			}

			//优先检查 method的RequestMapping
			final RequestMapping requestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
			//处理RequestMapping 含有指定输出类型
			if (requestMapping != null && requestMapping.produces() != null && requestMapping.produces().length > 0) {
				return drawView(request, response, ex, requestMapping.produces()[0]);
			}
			//其次检查controller的RequestMapping
			final RequestMapping classRequestMapping = handlerMethod.getMethod().getDeclaringClass()
					.getAnnotation(RequestMapping.class);
			if (classRequestMapping != null && classRequestMapping.produces() != null
					&& classRequestMapping.produces().length > 0) {//处理RequestMapping 含有指定输出类型
				return drawView(request, response, ex, classRequestMapping.produces()[0]);
			}

			final ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
			//存在注解ResponseBody,则认为返回json
			if (responseBody != null) {
				return drawJson(request, response, ex);
			}
			return drawJson(request, response, ex);
		}
		return drawJson(request, response, ex);
	}

	private ModelAndView drawView(HttpServletRequest request, HttpServletResponse response, Exception ex,
			String contentType) {
		contentType = contentType.toLowerCase();
		if (contentType.startsWith(MediaType.TEXT_HTML_VALUE)) {
			return drawHtml(request, response, ex);
		} else {
			return drawJson(request, response, ex);
		}
	}

	/**
	 * 获取请求要求的返回类型
	 * @param request
	 * @return
	 */
	private String getRequestContentType(HttpServletRequest request) {
		String contentType = request.getContentType();
		if (contentType == null) {
			contentType = request.getHeader("Accept");
		}
		return contentType;
	}

	private ModelAndView drawHtml(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		return getModelAndView(ex);
	}

	private ModelAndView getModelAndView(Exception exception) {
		return new ModelAndView("5xx_error_view");
	}

	/**
	 * 处理json返回结果
	 *
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView drawJson(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		final MappingJackson2JsonView jackson2JsonView = new MappingJackson2JsonView();
		jackson2JsonView.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		ModelAndView modelAndView = new ModelAndView(jackson2JsonView);
		ErrorResponse errorResponse = getErrorCode(ex);
		if (errorResponse.getCode() == CommonErrors.SERVICE_INTERNAL_ERROR.getCode()) {
			//组装错误消息,把用户传过来的参数打印出来
			String message = "server internal error,x-auth-token=";
			final String authToken = request.getHeader("x-auth-token");
			message = message + authToken;
			message = message + "\nqueryString=" + request.getQueryString();
			logger.error(message, ex);
		} else if (errorResponse.getCode() == CommonErrors.BAD_REQUEST.getCode()) {
			logger.warn("bad request,url=" + request.getRequestURI() + ", message=" + errorResponse.getMessage(), ex);
		} else {
			logger.warn("ex, message=" + errorResponse.getMessage(), ex);
		}
		//服务器内部错误,设置响应码为500,方便系统统计
		if (errorResponse.getCode() == CommonErrors.SERVICE_INTERNAL_ERROR.getCode()
				|| errorResponse.getCode() == CommonErrors.BAD_REQUEST.getCode()) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
		modelAndView.addObject("code", errorResponse.getCode());
		if (errorResponse.getData() != null) {
			modelAndView.addObject("data", errorResponse.getData());
		}
		if (errorResponse.getMessage() != null) {
			modelAndView.addObject("message", errorResponse.getMessage());
		}
		return modelAndView;
	}

	private ErrorResponse getErrorCode(Exception ex) {
		if (ex instanceof ServerBizException) {//业务异常，此处不打印错误日志
			final ServerBizException ServerBizException = (ServerBizException) ex;
			final String customMessage = ServerBizException.getCustomMessage();
			if (customMessage != null) {//自定义信息
				return ErrorResponse.create(ServerBizException.getErrorResponse().getCode(), customMessage);
			}
			return ServerBizException.getErrorResponse();
		} else if (ex instanceof ServletException) {//spring exception
			//返回非法参数错误，此处采用自定义的描述信息
			return ErrorResponse.create(CommonErrors.BAD_REQUEST.getCode(), ex.getMessage());
		} else if (ex instanceof HttpMessageConversionException) {
			//返回非法参数错误，此处采用自定义的描述信息
			return ErrorResponse.create(CommonErrors.BAD_REQUEST.getCode(), ex.getMessage());
		} else if (ex instanceof AccessDeniedException) {//增加自定义的security权限不足的错误。这样页面可以打印
			return ErrorResponse.create(CommonErrors.PERMISSION_DENIED.getCode(), ex.getMessage());
		}
		return CommonErrors.SERVICE_INTERNAL_ERROR;
	}

}
