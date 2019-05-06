package com.isbing.security.controller;

import com.isbing.security.bean.PageBean;
import com.isbing.security.bean.SysUser;
import com.isbing.security.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/28 17:05
 */
@RequestMapping("user")
@Controller
public class UserController {

	@Resource
	private UserService userService;

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody SysUser user) {
		return userService.create(user);
	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("getAll")
	@ResponseBody
	public PageBean getAll() {
		return userService.getAll();
	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@GetMapping("getById")
	@ResponseBody
	public SysUser getById(@RequestParam(value = "id") Integer id) {
		return userService.getById(id);
	}

	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@PutMapping("update")
	@ResponseBody
	public void update(@RequestBody SysUser user) {
		userService.update(user);
	}

}
