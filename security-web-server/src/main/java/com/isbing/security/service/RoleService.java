package com.isbing.security.service;

import com.isbing.security.bean.PageBean;
import com.isbing.security.bean.Role;
import com.isbing.security.dao.RoleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/28 9:52
 */
@Service
public class RoleService {
	@Resource
	private RoleDao roleDao;

	@Resource
	private RolePermissionService rolePermissionService;

	@Resource
	private UserRoleService userRoleService;

	public PageBean getAll() {
		return PageBean.builder().content(roleDao.getAll()).build();
	}

	public Integer create(Role role) {
		roleDao.insert(role);
		return role.getId();
	}

	public Role getById(int id) {
		return roleDao.getById(id);
	}

	public void update(Role role) {
		roleDao.update(role);
	}

	public List<Role> findByIds(List<Integer> roleIdList) {
		return roleDao.findByIds(roleIdList);
	}

	/**
	 * 删除整个角色 需要 将 用户-角色 删掉。角色-权限 删掉
	 * @param id
	 */
	public void deleteById(Integer id) {
		// 删除用户拥有的这个角色
		userRoleService.deleteByRoleId(id);
		// 删除这个这个角色 设置过的权限
		rolePermissionService.deleteByRoleId(id);
		// 删除角色
		roleDao.deleteById(id);
	}
}
