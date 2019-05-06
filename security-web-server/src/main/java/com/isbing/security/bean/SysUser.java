package com.isbing.security.bean;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by song bing
 * Created time 2019/3/28 17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysUser {
	private int id;
	private String userName;//用户名
	private String nickName;//昵称
	private String password;//密码
	private Date createTime;//创建时间
	private String roleStr;//角色字符串 页面展示

	private Set<Menu> menus = new LinkedHashSet<>();//菜单 用于动态展示菜单
	protected Set<String> permissionRoleCodes = new LinkedHashSet<>();//权限名、角色名 用于校验权限

	public String getUsername() {
		return userName;
	}
}
