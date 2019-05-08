package com.isbing.tool.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by song bing
 * Created time 2019/5/8 16:03
 */
public class DateTimeUtils {

	/**
	 * 获取当前时间的 年月日时分秒
	 * @param date
	 * @return
	 */
	public static String getYyyyMmDdHhMmSs(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(date);
	}

}
