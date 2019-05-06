package com.isbing.security.common.bean;

import java.io.Serializable;

/**
 * Response 返回结果抽象类
 * Created by song bing
 * Created time 2019/4/23 16:53
 */
public abstract class Result implements Serializable {
	public static final int SUCCESS_CODE = 200;
	protected int code;//响应码
	protected Object data;//返回的数据
	protected String message;//消息

	public Result(int code, Object data, String message) {
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public Result() {

	}

	public int getCode() {
		return code;
	}

	protected void setCode(int code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	protected void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	protected void setMessage(String message) {
		this.message = message;
	}
}
