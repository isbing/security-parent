package com.isbing.security.common.bean;

/**
 * 错误结果返回封装
 * Created by song bing
 * Created time 2019/4/23 19:08
 */
public class ErrorResponse extends Result {
	/**
	 * 资源未发现
	 */
	public static final ErrorResponse NOT_FOUND = create(404, "资源未发现");

	public ErrorResponse(int code, String message) {
		super(code, null, message);
	}

	public ErrorResponse() {

	}

	/**
	 * 创建一个新的错误对象
	 * @param code
	 * @param message
	 * @return
	 */
	public static final ErrorResponse create(int code, String message) {
		return new ErrorResponse(code, message);
	}
}

