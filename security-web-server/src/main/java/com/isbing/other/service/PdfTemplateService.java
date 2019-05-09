package com.isbing.other.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.isbing.other.dao.PdfTemplateDao;
import com.isbing.security.bean.PageBean;
import com.isbing.tool.common.bean.PageParam;
import com.isbing.tool.common.bean.PdfTemplate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by song bing
 * Created time 2019/5/8 15:46
 */
@Service
public class PdfTemplateService {

	@Resource
	private PdfTemplateDao pdfTemplateDao;

	/**
	 * mybatis分页插件
	 * @param pageParam
	 * @return
	 */
	public PageInfo listAll(PageParam pageParam) {
		// 第一个参数是第几页；第二个参数是每页显示条数
		PageHelper.startPage(pageParam.getPage(), pageParam.getSize());
		List<PdfTemplate> pdfTemplateList = pdfTemplateDao.listAllByPaging();
		return new PageInfo<>(pdfTemplateList, pageParam.getSize());
	}
}
