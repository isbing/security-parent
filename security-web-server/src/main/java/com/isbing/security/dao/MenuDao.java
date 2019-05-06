package com.isbing.security.dao;

import com.isbing.security.bean.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/20 14:19
 */
@Mapper
public interface MenuDao {

	List<Menu> getAllFirstLevel();

	Integer insert(Menu menu);

	Menu getById(Integer id);

	void update(Menu menu);

	List<Menu> getAllSecondLevel(int parentId);

	List<Menu> getAllSecondLevelNoId();
}
