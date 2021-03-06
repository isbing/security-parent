package com.isbing.security.dao;

import com.isbing.security.bean.Role;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/27 19:24
 */
public interface RoleDao {

	List<Role> getAll();

	void insert(Role role);

	Role getById(int id);

	void update(Role role);

	List<Role> findByIds(List<Integer> roleIdList);

	void deleteById(Integer id);
}
