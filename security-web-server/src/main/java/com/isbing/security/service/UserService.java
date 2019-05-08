package com.isbing.security.service;

import com.isbing.security.bean.PageBean;
import com.isbing.security.bean.Role;
import com.isbing.security.bean.SysUser;
import com.isbing.security.common.exception.ServerBizException;
import com.isbing.security.common.util.JsonUtil;
import com.isbing.security.dao.UserDao;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by song bing
 * Created time 2019/3/28 17:06
 */
@Service
public class UserService {
	@Resource
	private UserDao userDao;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private RoleService roleService;

	@Resource
	private BCryptPasswordEncoder passwordEncoder;

	public Integer create(SysUser user) {
		ServerBizException.checkArgument(StringUtils.isNotBlank(user.getPassword()), "密码不能为空");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.insert(user);
		return user.getId();
	}

	public PageBean getAll() {
		List<SysUser> userList = userDao.getAll();
		// 设置每个用户 对应的 所有角色
		if (CollectionUtils.isNotEmpty(userList)) {
			userList.forEach(user -> {
				int userId = user.getId();
				List<Integer> roleIdList = userRoleService.getRoleByUserId(userId);
				if (CollectionUtils.isNotEmpty(roleIdList)) {
					List<Role> roleList = roleService.findByIds(roleIdList);
					if (CollectionUtils.isNotEmpty(roleList)) {
						List<String> roleNameList = roleList.stream().map(Role::getName).collect(Collectors.toList());
						StringBuilder sb = new StringBuilder();
						roleNameList.forEach(roleName -> sb.append(roleName).append("，"));
						String str = sb.toString();
						// 设置 用户已拥有的角色
						user.setRoleStr(str.substring(0, str.lastIndexOf("，")));
					}
				}
			});
		}
		return PageBean.builder().content(userList).build();
	}

	public SysUser getById(Integer id) {
		return userDao.getById(id);
	}

	public void update(SysUser user) {
		ServerBizException.checkArgument(StringUtils.isNotBlank(user.getPassword()), "密码不能为空");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userDao.update(user);
	}

	public SysUser getByUsername(String username) {
		return userDao.getByUsername(username);
	}

	/**
	 * 删除用户
	 * @param userId
	 */
	public void deleteById(Integer userId) {
		// 只有admin用户才能删除别的用户
		// todo 需要完善 获取当前用户
		ServerBizException.checkPermission(userId == 1, "只有admin用户才能删除");
		// 删除用户-角色的对应关系
		userRoleService.clearRole(userId);
		// 删除当前用户
		userDao.deleteById(userId);
	}
}
