package com.isbing.other.controller;

import com.github.pagehelper.PageInfo;
import com.isbing.other.service.PdfTemplateService;
import com.isbing.security.bean.PageBean;
import com.isbing.security.common.util.JsonUtil;
import com.isbing.tool.common.bean.PageParam;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/5/9 12:06
 */
@RestController
@RequestMapping("pdfTemplate")
public class PdfTemplateController {

	@Resource
	private PdfTemplateService pdfTemplateService;

	/**
	 * 模板数据的分页结果
	 * @param pageParam
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_POI')")
	@GetMapping("listAll")
	public PageInfo getAllFirstLevel(PageParam pageParam) {
		return pdfTemplateService.listAll(pageParam);
	}
}
