package com.isbing.security.controller;

import com.isbing.security.service.UserRoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/28 17:26
 */
@RequestMapping("userRole")
@Controller
public class UserRoleController {
	@Resource
	private UserRoleService userRoleService;

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getRoleByUserId")
	@ResponseBody
	public List<Integer> getRoleByUserId(@RequestParam(value = "userId") int userId) {
		return userRoleService.getRoleByUserId(userId);
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PostMapping("setUserRole")
	@ResponseBody
	public void setUserRole(@RequestParam(value = "userId") int userId,
			@RequestParam(value = "roleIdStr") String roleIdStr) {
		userRoleService.setUserRole(userId, roleIdStr);
	}
}
