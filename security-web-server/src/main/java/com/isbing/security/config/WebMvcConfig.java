package com.isbing.security.config;

import com.google.common.collect.Lists;
import com.isbing.security.advice.CommonResponseDataAdvice;
import com.isbing.security.advice.GlobalExceptionAdvice;
import com.isbing.security.advice.RequestTokenMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/4/23 16:39
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

	@Override
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.requestMappingHandlerAdapter();
		// 添加自定义的 返回封装结果
		List<ResponseBodyAdvice<?>> responseBodyAdvice = Lists.newArrayList(new CommonResponseDataAdvice());
		requestMappingHandlerAdapter.setResponseBodyAdvice(responseBodyAdvice);
		return requestMappingHandlerAdapter;
	}

	@Override
	public HandlerExceptionResolver handlerExceptionResolver() {
		return new GlobalExceptionAdvice();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new RequestTokenMethodArgumentResolver());
	}

	/**
	 * 继承这个类后 在没有使用security时  login.html都访问不到
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		//		registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/resource/css/");
		//		registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/resource/fonts/");
		//		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/resource/img/");
		//		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/resource/js/");
	}

}
