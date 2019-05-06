package com.isbing.security.advice;

import com.isbing.security.annotation.NoWapperResponse;
import com.isbing.security.common.bean.Result;
import com.isbing.security.common.bean.SuccessMessage;
import com.isbing.security.common.bean.SuccessResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by song bing
 * Created time 2019/4/23 16:51
 */
public class CommonResponseDataAdvice implements ResponseBodyAdvice {
	@Override
	public boolean supports(MethodParameter methodParameter, Class aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
			ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
		//不需要进行结果包装
		if (Objects.nonNull(methodParameter.getMethodAnnotation(NoWapperResponse.class))) {
			return o;
		}
		ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
		final HttpServletRequest servletRequest = request.getServletRequest();
		//如果是spring cloud,则不做封装,方便spring cloud 调用
		final String servletPath = servletRequest.getServletPath();
		if (servletPath.startsWith("/rpc")) {
			return o;
		}

		if (isHtml(mediaType)) {
			return o;
		}

		// {"code":200,"data":null,"message":o}
		if (o == null) {//没有返回数据
			return SuccessMessage.create("操作成功");
		} else if (o instanceof Result) {//已经是封装后的result
			return o;
		} else if (o instanceof String) {
			return SuccessMessage.create((String) o);
		}
		// {"code":200,"data":o,"message":null}
		return SuccessResponse.create(o);
	}

	/**
	 * 返回是否是html
	 * @param mediaType
	 * @return
	 */
	private boolean isHtml(MediaType mediaType) {
		return mediaType.includes(MediaType.TEXT_HTML);
	}
}
