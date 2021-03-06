package com.isbing.security.controller;

import com.isbing.security.bean.PageBean;
import com.isbing.security.bean.Permission;
import com.isbing.security.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/26 16:02
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Resource
	private PermissionService permissionService;

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getAll")
	@ResponseBody
	public PageBean getAll() {
		return permissionService.getAll();
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody Permission permission) {
		return permissionService.insert(permission);
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getById")
	@ResponseBody
	public Permission getById(@RequestParam(value = "id") int id) {
		return permissionService.getById(id);
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PutMapping("update")
	@ResponseBody
	public void update(@RequestBody Permission permission) {
		permissionService.update(permission);
	}
}
