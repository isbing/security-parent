package com.isbing.tool.common.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by song bing
 * Created time 2019/5/9 15:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageParam {
	private Integer page;
	private Integer size;
}
