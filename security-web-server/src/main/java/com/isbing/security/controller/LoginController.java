package com.isbing.security.controller;

import com.google.common.collect.Maps;
import com.isbing.security.bean.CurrentUser;
import com.isbing.security.bean.Menu;
import com.isbing.security.common.util.JsonUtil;
import com.isbing.security.service.MenuService;
import com.isbing.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by song bing
 * Created time 2019/4/25 15:24
 */
@Controller
public class LoginController {

	@Resource
	private UserService userService;

	@Resource
	private MenuService menuService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@RequestMapping("/")
	public RedirectView loginPage() {
		return new RedirectView("/login.html");
	}

	/**
	 * 根据认证信息获取用户信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public Map getUser(HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CurrentUser) {
			CurrentUser currentUser = (CurrentUser) principal;
			Map<String, Object> map = Maps.newHashMap();
			// 后台管理菜单
			map.put("menuUrl", getStaffMenu(currentUser.getMenus()));
			map.put("userName", currentUser.getNickName());
			// 当前用户的所有权限码
			Collection<? extends GrantedAuthority> authorities = currentUser.getAuthorities();
			map.put("authorities", StringUtils.join(authorities, ","));

			return map;
		} else {
			Map<String, Object> data = Maps.newHashMap();
			data.put("code", 1003);
			return data;
		}
	}

	//设置后台菜单
	private Map getStaffMenu(Set<Menu> menusSet) {
		Map<String, Map> mapMenu = new LinkedHashMap<>();
		Iterator<Menu> iter = menusSet.iterator();
		while (iter.hasNext()) {
			Map map = new LinkedHashMap();
			Menu menu = iter.next();
			int parentId = menu.getParentId();
			Menu parentMenu = menuService.getById(parentId);
			String parentMenuName = parentMenu.getUrl();
			if (mapMenu.get(parentMenuName) != null) {
				map = mapMenu.get(parentMenuName);
			}
			map.put(menu.getName(), menu.getUrl());
			mapMenu.put(parentMenuName, map);
		}
		return mapMenu;
	}
}
