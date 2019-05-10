package com.isbing.other.controller;

import com.isbing.other.service.FileService;
import com.isbing.other.service.PdfTemplateService;
import com.isbing.security.annotation.NoWapperResponse;
import com.isbing.tool.common.bean.PdfTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * 文件上传的controller
 * Created by song bing
 * Created time 2019/5/8 15:23
 */
@RestController
@RequestMapping("/file")
public class FileController {

	@Resource
	private FileService fileService;

	@Resource
	private PdfTemplateService pdfTemplateService;

	/**
	 * 通用上传文件方法
	 * @param file
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_POI')")
	@PostMapping(value = "/uploadFile")
	public void singleSave(@RequestParam("file") MultipartFile file) {
		fileService.uploadFile(file);
	}

	/**
	 * 预览模板文件
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping(value = "/viewTemplate", produces = "application/json;charset=UTF-8")
	@PreAuthorize("hasAnyRole('ROLE_POI')")
	public void view(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "id", required = false) Integer id) {
		FileInputStream bis = null;
		OutputStream os = null;
		try {
			PdfTemplate pdfTemplate = pdfTemplateService.getById(id);
			if (Objects.isNull(pdfTemplate)) {
				return;
			}
			response.setContentType("text/html; charset=UTF-8");
			response.setContentType("application/pdf");
			bis = new FileInputStream(pdfTemplate.getViewLocation());
			os = response.getOutputStream();
			int count = 0;
			byte[] buffer = new byte[1024 * 1024];
			while ((count = bis.read(buffer)) != -1) {
				os.write(buffer, 0, count);
			}
			os.flush();
		} catch (Exception e) {
			//logger.info(e.getMessage());
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					//logger.info(e.getMessage());
				}
			}
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					//logger.info(e.getMessage());
				}
			}
		}
	}
}
