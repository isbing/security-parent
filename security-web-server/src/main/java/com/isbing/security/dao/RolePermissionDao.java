package com.isbing.security.dao;

import com.isbing.security.bean.RolePermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by song bing
 * Created time 2019/3/28 10:13
 */
@Mapper
public interface RolePermissionDao {

	List<RolePermission> getByRoleId(int roleId);

	void clearPermission(int roleId);

	void insertRolePermission(List<Map<String, Integer>> data);

	void deleteByRoleId(Integer roleId);
}
