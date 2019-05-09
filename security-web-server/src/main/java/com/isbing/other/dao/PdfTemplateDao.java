package com.isbing.other.dao;

import com.isbing.tool.common.bean.PdfTemplate;

import java.util.List;

/**
 * Created by song bing
 * Created time 2019/5/8 19:40
 */
public interface PdfTemplateDao {
	PdfTemplate getByName(String fileName);

	void insert(PdfTemplate pdfTemplate);

	List<PdfTemplate> listAllByPaging();
}
