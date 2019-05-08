package com.isbing.security.service;

import com.isbing.security.bean.CurrentUser;
import com.isbing.security.bean.SysUser;
import com.isbing.security.common.exception.ServerBizException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 校验用户 并设置 登录用户的属性
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
		// 这里校验用户 并抛出异常之后 被loginFailureHandler()方法接收
		ServerBizException.checkResourceExist(sysUser, "用户名不存在");
		// 获取用户的菜单 以及所有的权限码
		sysUser = menuService.getMenusByUser(sysUser);
		//创建 Security User
		CurrentUser currentUser = new CurrentUser();
		BeanUtils.copyProperties(sysUser, currentUser);
		return currentUser;
	}
}
