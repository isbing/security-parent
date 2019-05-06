package com.isbing.security.common.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by song bing
 * Created time 2019/4/23 17:17
 */
public class SuccessMessage extends Result {
	private SuccessMessage(String message) {
		super(SUCCESS_CODE, null, message);
	}

	public SuccessMessage() {
	}

	/**
	 * 创建一个返回成功信息的对象
	 * @param message
	 * @return
	 */
	public static final SuccessMessage create(String message) {
		//判断是否为空
		if (StringUtils.isBlank(message)) {
			throw new RuntimeException("message is blank. message = " + message);
		}
		return new SuccessMessage(message);
	}
}
