package com.isbing.security.common.bean;

/**
 * Created by song bing
 * Created time 2019/4/23 19:11
 */
public class CommonErrors {
	public static final ErrorResponse INVALID_ARGUMENTS = ErrorResponse.create(10101, "非法的参数");
	public static final ErrorResponse SERVICE_INTERNAL_ERROR = ErrorResponse.create(100102, "请求数据出错了");
	public static final ErrorResponse RESOURCE_NOT_FOUND = ErrorResponse.create(100103, "资源未发现");
	public static final ErrorResponse PERMISSION_DENIED = ErrorResponse.create(100104, "无权访问");
	public static final ErrorResponse ILLEGAL_STATE = ErrorResponse.create(100105, "非法的状态");
	public static final ErrorResponse BAD_REQUEST = ErrorResponse.create(100106, "请求格式错误");

}
