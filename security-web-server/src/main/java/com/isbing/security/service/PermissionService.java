package com.isbing.security.service;

import com.isbing.security.bean.PageBean;
import com.isbing.security.bean.Permission;
import com.isbing.security.dao.PermissionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/26 16:03
 */
@Service
public class PermissionService {
	@Resource
	private PermissionDao permissionDao;

	public PageBean getAll() {
		return PageBean.builder().content(permissionDao.getAll()).build();
	}

	public Integer insert(Permission permission) {
		permissionDao.insert(permission);
		return permission.getId();
	}

	public Permission getById(int id) {
		return permissionDao.getById(id);
	}

	public void update(Permission permission) {
		permissionDao.update(permission);
	}
}
