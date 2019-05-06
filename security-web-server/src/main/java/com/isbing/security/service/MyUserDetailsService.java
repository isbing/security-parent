package com.isbing.security.service;

import com.isbing.security.bean.CurrentUser;
import com.isbing.security.bean.SysUser;
import com.isbing.security.service.MenuService;
import com.isbing.security.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/5/6 14:58
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Resource
	private UserService userService;

	@Resource
	private MenuService menuService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = userService.getByUsername(username);
		// 此时 user下有set<Role> role下有set<Permission>
		sysUser = menuService.getMenusByUser(sysUser);
		//创建 Security User
		CurrentUser currentUser = new CurrentUser();
		BeanUtils.copyProperties(sysUser, currentUser);
		return currentUser;
	}
}
