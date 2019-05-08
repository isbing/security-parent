package com.isbing.security.config;

import com.google.common.collect.Maps;
import com.isbing.security.bean.CurrentUser;
import com.isbing.security.common.bean.ErrorResponse;
import com.isbing.security.common.bean.SuccessResponse;
import com.isbing.security.common.util.JsonUtil;
import com.isbing.security.service.TokenServiceJWT;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by song bing
 * Created time 2019/4/25 14:46
 */
@Configuration
public class SecurityHandlerConfig {

	@Resource
	private TokenServiceJWT tokenService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 登陆成功，返回Token
	 *
	 * @return
	 */
	@Bean
	public AuthenticationSuccessHandler loginSuccessHandler() {
		return (request, response, authentication) -> {
			response.setHeader("Content-type", "application/json; charset=utf-8");
			// 拿到sessionId
			//String token = request.getSession().getId();
			String token = UUID.randomUUID().toString().replaceAll("-", "");
			CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// todo 用户登录一次 会生成一个token 然后将其设置过期时间。或者退出登录 将当前token删除

			stringRedisTemplate.opsForValue().set("token:" + token, JsonUtil.toJson(currentUser), 30, TimeUnit.MINUTES);

			// 用户登录成功 返回给前端 token
			Map<String, Object> map = Maps.newHashMap();
			map.put("token", token);
			SuccessResponse result = SuccessResponse.create(map);

			response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
		};
	}

	/**
	 * 登陆失败
	 *
	 * @return
	 */
	@Bean
	public AuthenticationFailureHandler loginFailureHandler() {
		return (request, response, exception) -> {
			response.setHeader("Content-type", "application/json; charset=utf-8");
			ErrorResponse result = ErrorResponse.create(1001, "密码错误");
			if (exception instanceof InternalAuthenticationServiceException) {
				result = ErrorResponse.create(1001, exception.getMessage());
			}
			response.getWriter().write(Objects.requireNonNull(JsonUtil.toJson(result)));
		};

	}

	/**
	 * 未登录，返回401
	 *
	 * @return
	 */
	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authException) -> {
			//			ResponseInfo info = new ResponseInfo(HttpStatus.UNAUTHORIZED.value() + "", "请先登录");
			//			ResponseUtil.responseJson(response, HttpStatus.UNAUTHORIZED.value(), info);
		};
	}

	/**
	 * 退出处理
	 *
	 * @return
	 */
	@Bean
	public LogoutSuccessHandler logoutSussHandler() {
		return (request, response, authentication) -> {
			//			ResponseInfo info = new ResponseInfo(HttpStatus.OK.value() + "", "退出成功");
			//
			//			String token = TokenFilter.getToken(request);
			//			tokenService.deleteToken(token);
			//
			//			ResponseUtil.responseJson(response, HttpStatus.OK.value(), info);
		};

	}

}
