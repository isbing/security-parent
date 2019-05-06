package com.isbing.security.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ValueConstants;

import java.lang.annotation.*;

/**
 * request token 注解
 * Created by song bing
 * Created time 2019/4/23 19:22
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestToken {
	/**
	 * Alias for {@link #name}.
	 */
	@AliasFor("name")
	String value() default "x-auth-token";

	/**
	 * The name of the request header to bind to.
	 * @since 4.2
	 */
	@AliasFor("value")
	String name() default "x-auth-token";

	/**
	 * Whether the header is required.
	 * <p>Defaults to {@code true}, leading to an exception being thrown
	 * if the header is missing in the request. Switch this to
	 * {@code false} if you prefer a {@code null} value if the header is
	 * not present in the request.
	 * <p>Alternatively, provide a {@link #defaultValue}, which implicitly
	 * sets this flag to {@code false}.
	 */
	boolean required() default false;

	/**
	 * The default value to use as a fallback.
	 * <p>Supplying a default value implicitly sets {@link #required} to
	 * {@code false}.
	 */
	String defaultValue() default ValueConstants.DEFAULT_NONE;
}
