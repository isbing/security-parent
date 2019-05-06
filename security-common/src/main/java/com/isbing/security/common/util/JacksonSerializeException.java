package com.isbing.security.common.util;

/**
 * Created by song bing
 * Created time 2019/4/25 17:55
 */
public class JacksonSerializeException extends RuntimeException {
	public JacksonSerializeException() {
	}

	public JacksonSerializeException(String message) {
		super(message);
	}

	public JacksonSerializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public JacksonSerializeException(Throwable cause) {
		super(cause);
	}

	public JacksonSerializeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
