package com.isbing.security.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.isbing.security.bean.*;
import com.isbing.security.dao.MenuDao;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by song bing
 * Created time 2019/3/20 14:19
 */
@Service
public class MenuService {
	@Resource
	private MenuDao menuDao;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private RoleService roleService;

	@Resource
	private RolePermissionService rolePermissionService;

	@Resource
	private PermissionService permissionService;

	public PageBean getAllFirstLevel() {
		return PageBean.builder().content(menuDao.getAllFirstLevel()).build();
	}

	public List<Menu> getAllSecondLevel(int parentId) {
		List<Menu> menuList = menuDao.getAllSecondLevel(parentId);
		// 增加冗余字段
		if (CollectionUtils.isNotEmpty(menuList)) {
			Menu parentMenu = getById(parentId);
			if (Objects.nonNull(parentMenu)) {
				menuList.forEach(menu -> menu.setParentUrl(parentMenu.getUrl()));
			}
		}
		return menuList;
	}

	public Integer create(Menu menu) {
		menuDao.insert(menu);
		return menu.getId();
	}

	public Menu getById(Integer id) {
		return menuDao.getById(id);
	}

	public void update(Menu menu) {
		menuDao.update(menu);
	}

	public List<Menu> getAllSecondLevelNoId() {
		return menuDao.getAllSecondLevelNoId();
	}

	/**
	 * 获取用户所有的菜单
	 * @param user
	 * @return
	 */
	public SysUser getMenusByUser(SysUser user) {
		// 菜单
		Set<Menu> menuSet = Sets.newHashSet();
		Set<String> codeList = new LinkedHashSet<>();

		List<Integer> userRoleIdList = userRoleService.getRoleByUserId(user.getId());
		if (CollectionUtils.isNotEmpty(userRoleIdList)) {
			List<Role> roleList = roleService.findByIds(userRoleIdList);
			if (CollectionUtils.isNotEmpty(roleList)) {
				// 根据role 获取 权限
				roleList.forEach(role -> {
					codeList.add(role.getCode());

					List<Integer> permissionIdList = rolePermissionService.getByRoleId(role.getId());
					if (CollectionUtils.isNotEmpty(permissionIdList)) {
						permissionIdList.forEach(permissionId -> {
							Permission permission = permissionService.getById(permissionId);
							if (Objects.nonNull(permission)) {
								codeList.add(permission.getCode());

								Menu menu = menuDao.getById(permission.getMenuId());
								if (Objects.nonNull(menu)) {
									menuSet.add(menu);
								}
							}
						});
					}
				});
			}
		}
		user.setMenus(menuSet);// 设置到user
		user.setPermissionRoleCodes(codeList);
		return user;
	}
}
