package com.isbing.security.controller;

import com.isbing.security.annotation.RequestToken;
import com.isbing.security.bean.Menu;
import com.isbing.security.bean.PageBean;
import com.isbing.security.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/20 14:19
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	@Resource
	private MenuService menuService;

	/**
	 * 菜单管理  这样的 不用分页
	 * 找所有菜单的一级菜单
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getAllFirstLevel")
	@ResponseBody
	public PageBean getAllFirstLevel(@RequestToken String token) {

		return menuService.getAllFirstLevel();
	}

	/**
	 * 菜单管理  这样的 不用分页
	 * 找所有菜单的二级菜单
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getAllSecondLevel")
	@ResponseBody
	public List<Menu> getAllSecondLevel(@RequestParam(value = "parentId", required = false) int parentId) {
		return menuService.getAllSecondLevel(parentId);
	}

	/**
	 * 菜单管理  这样的 不用分页
	 * 找所有菜单的二级菜单
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getAllSecondLevelNoId")
	@ResponseBody
	public List<Menu> getAllSecondLevelNoId() {
		return menuService.getAllSecondLevelNoId();
	}

	/**
	 * 新增一级菜单
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody Menu menu) {
		return menuService.create(menu);
	}

	/**
	 * 根据ID查找菜单信息
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@GetMapping("getById")
	@ResponseBody
	public Menu getById(@RequestParam(value = "id") Integer id) {
		return menuService.getById(id);
	}

	@PreAuthorize("hasAnyRole('ROLE_SECURITY')")
	@PutMapping("update")
	@ResponseBody
	public void update(@RequestBody Menu menu) {
		menuService.update(menu);
	}
}
