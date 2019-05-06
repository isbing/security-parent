package com.isbing.security.common.bean;

/**
 * 正常响应
 * Created by song bing
 * Created time 2019/4/23 16:53
 */
public class SuccessResponse extends Result {

	private SuccessResponse(Object data, String message) {
		super(SUCCESS_CODE, data, message);
	}

	private SuccessResponse(Object data) {
		super(SUCCESS_CODE, data, null);
	}

	public SuccessResponse() {
	}

	/**
	 * 返回正常响应体
	 * @param data 业务数据
	 * @return
	 */
	public static final SuccessResponse create(Object data) {
		return new SuccessResponse(data);
	}

	/**
	 * 返回正常响应体
	 * @param data 业务数据
	 * @param message 提示消息
	 * @return
	 */
	public static final SuccessResponse create(Object data, String message) {
		return new SuccessResponse(data, message);
	}
}
