package com.isbing.security.common.exception;

import com.isbing.security.common.bean.CommonErrors;
import com.isbing.security.common.bean.ErrorResponse;

import java.util.Objects;

/**
 * Created by song bing
 * Created time 2019/4/23 19:13
 */
public class ServerBizException extends RuntimeException {
	private ErrorResponse errorResponse;
	private String customMessage;//个性化信息

	public ServerBizException(ErrorResponse errorResult) {
		super(errorResult.getMessage());
		this.errorResponse = errorResult;
	}

	public ServerBizException(ErrorResponse errorResult, String customMessage) {
		super(customMessage);
		this.errorResponse = errorResult;
		this.customMessage = customMessage;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public String getCustomMessage() {
		return customMessage;
	}

	/**
	 * 检查业务参数，如果不合法，则抛出异常
	 * @param expression 表达式，如果为false 则抛出ServerBizException异常
	 * @param errorMessage 错误描述信息
	 */
	public static final void checkArgument(boolean expression, String errorMessage) {
		check(expression, errorMessage, CommonErrors.INVALID_ARGUMENTS);
	}

	/**
	 * 检查用户权限问题
	 * @param expression 表达式，如果为false 则抛出ServerBizException异常
	 * @param errorMessage 错误描述信息
	 */
	public static final void checkPermission(boolean expression, String errorMessage) {
		check(expression, errorMessage, CommonErrors.PERMISSION_DENIED);
	}

	/**
	 * 检查访问的资源是否存在
	 * @param resource 待检查的资源
	 * @param errorMessage 错误描述信息
	 */
	public static final void checkResourceExist(Object resource, String errorMessage) {
		check(Objects.nonNull(resource), errorMessage, CommonErrors.RESOURCE_NOT_FOUND);
	}

	/**
	 * 检查记录的状态是否合法
	 * @param expression 表达式，如果为false 则抛出ServerBizException异常
	 * @param errorMessage 错误描述信息
	 */
	public static final void checkState(boolean expression, String errorMessage) {
		check(expression, errorMessage, CommonErrors.ILLEGAL_STATE);
	}

	/**
	 * 抛出一个非法参数的异常
	 * @param reason 原因
	 */
	public static final void newArgumentException(String reason) {
		throw new ServerBizException(CommonErrors.INVALID_ARGUMENTS, reason);
	}

	private static void check(boolean expression, String errorMessage, ErrorResponse errorResponse) {
		if (!expression) {
			throw new ServerBizException(errorResponse, errorMessage);
		}
	}
}
