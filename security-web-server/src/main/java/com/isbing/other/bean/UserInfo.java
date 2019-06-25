package com.isbing.other.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by song bing
 * Created time 2019/6/25 15:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
	private String name;
	private Integer age;
	private String sex;
	private String love;
	private String job;
	private String mobile;
}
