package com.isbing.other.controller;

import com.isbing.other.service.PdfTemplateService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传的controller
 * Created by song bing
 * Created time 2019/5/8 15:23
 */
@RestController
@RequestMapping("/file")
public class FileController {

	@Resource
	private PdfTemplateService pdfTemplateService;

	/**
	 * 通用上传文件方法
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public void singleSave(@RequestParam("file") MultipartFile file) {
		pdfTemplateService.uploadFile(file);
	}

}
