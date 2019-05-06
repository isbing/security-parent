package com.isbing.security.filter;

import com.isbing.security.bean.CurrentUser;
import com.isbing.security.common.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * token过滤器
 * Created by song bing
 * Created time 2019/5/6 14:45
 */
@Configuration
public class TokenFilter extends OncePerRequestFilter {

	public static final String TOKEN_KEY = "x-auth-token";

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		String token = getToken(request);
		if (StringUtils.isNotBlank(token)) {
			String s = stringRedisTemplate.opsForValue().get("token:" + token);
			CurrentUser loginUser = JsonUtil.toObject(s, CurrentUser.class);
			if (loginUser != null) {
				//loginUser = checkLoginTime(loginUser);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser,
						null, loginUser.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 根据参数或者header获取token
	 *
	 * @param request
	 * @return
	 */
	public static String getToken(HttpServletRequest request) {
		String token = request.getParameter(TOKEN_KEY);
		if (StringUtils.isBlank(token)) {
			token = request.getHeader(TOKEN_KEY);
		}
		return token;
	}

}
