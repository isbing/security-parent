package com.isbing.security.annotation;

import java.lang.annotation.*;

/**
 * Created by song bing
 * Created time 2019/4/23 17:18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoWapperResponse {
}
