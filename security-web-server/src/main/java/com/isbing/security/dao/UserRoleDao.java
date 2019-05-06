package com.isbing.security.dao;

import com.isbing.security.bean.UserRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by song bing
 * Created time 2019/3/28 17:27
 */
@Mapper
public interface UserRoleDao {

	List<UserRole> getRoleByUserId(int userId);

	void clearRole(int userId);

	void insertUserRole(List<Map<String, Integer>> userRoleList);
}
