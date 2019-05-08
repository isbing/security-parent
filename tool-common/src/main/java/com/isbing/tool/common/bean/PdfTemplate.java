package com.isbing.tool.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by song bing
 * Created time 2019/5/8 15:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PdfTemplate {
	private Integer id;
	private String name;//模板名
	private Integer status;//状态
	private Date createTime;//创建时间
	private Date offlineTime;//下线时间
	private String viewLocation;//模板的地址
	private Integer operator;//操作人ID

	public static final class Status {
		public static final Integer STATUS_ON = 1;//上线
		public static final Integer STATUS_Off = 2;//下线
	}
}
