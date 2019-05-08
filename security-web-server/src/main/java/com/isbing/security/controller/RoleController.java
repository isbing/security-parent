package com.isbing.security.controller;

import com.isbing.security.bean.PageBean;
import com.isbing.security.bean.Role;
import com.isbing.security.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/28 9:51
 */
@RequestMapping("/role")
@Controller
public class RoleController {

	@Resource
	private RoleService roleService;

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getAll")
	@ResponseBody
	public PageBean getAll() {
		return roleService.getAll();
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody Role role) {
		return roleService.create(role);
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getById")
	@ResponseBody
	public Role getById(@RequestParam(value = "id") int id) {
		return roleService.getById(id);
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PutMapping("update")
	@ResponseBody
	public void update(@RequestBody Role role) {
		roleService.update(role);
	}

	/**
	 * 删除角色
	 * @param id
	 */
	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PutMapping("deleteById")
	@ResponseBody
	public void deleteById(@RequestParam(value = "id", required = false) Integer id) {
		roleService.deleteById(id);
	}
}
