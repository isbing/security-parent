package com.isbing.security.dao;

import com.isbing.security.bean.Permission;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/26 16:03
 */
public interface PermissionDao {

	List<Permission> getAll();

	void insert(Permission permission);

	Permission getById(int id);

	void update(Permission permission);
}
