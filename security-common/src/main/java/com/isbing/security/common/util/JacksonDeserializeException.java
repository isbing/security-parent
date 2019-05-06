package com.isbing.security.common.util;

/**
 * Created by song bing
 * Created time 2019/4/25 18:40
 */
public class JacksonDeserializeException extends RuntimeException {
	public JacksonDeserializeException() {
	}

	public JacksonDeserializeException(String message) {
		super(message);
	}

	public JacksonDeserializeException(String message, Throwable cause) {
		super(message, cause);
	}

	public JacksonDeserializeException(Throwable cause) {
		super(cause);
	}

	public JacksonDeserializeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
