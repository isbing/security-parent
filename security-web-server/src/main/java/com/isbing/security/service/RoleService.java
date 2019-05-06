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
}
