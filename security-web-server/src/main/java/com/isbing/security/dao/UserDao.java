package com.isbing.security.dao;

import com.isbing.security.bean.SysUser;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/28 17:06
 */
public interface UserDao {

	void insert(SysUser user);

	List<SysUser> getAll();

	SysUser getById(Integer id);

	void update(SysUser user);

	SysUser getByUsername(String username);

	void deleteById(Integer userId);
}
