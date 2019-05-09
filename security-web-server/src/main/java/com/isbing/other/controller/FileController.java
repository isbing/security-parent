package com.isbing.other.controller;

import com.isbing.other.service.FileService;
import org.springframework.security.access.prepost.PreAuthorize;
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
	private FileService fileService;

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

}
