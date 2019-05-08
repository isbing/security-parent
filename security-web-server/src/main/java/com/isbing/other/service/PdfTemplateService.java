package com.isbing.other.service;

import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import com.isbing.other.dao.ADao;
import com.isbing.other.dao.BDao;
import com.isbing.security.common.bean.ErrorResponse;
import com.isbing.security.common.exception.ServerBizException;
import com.isbing.security.dao.CDao;
import com.isbing.security.dao.MenuDao;
import com.isbing.tool.common.bean.PdfTemplate;
import com.isbing.tool.common.util.DateTimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * Created by song bing
 * Created time 2019/5/8 15:46
 */
@Service
public class PdfTemplateService {

	@Value("${server.env}")
	private String env;

	@Value("${file.windows.path}")
	private String windowPath;

	@Value("${file.linux.path}")
	private String linuxPath;

	@Resource
	private BDao pdfTemplateDao;

	/**
	 * 上传模板文件
	 * @param file
	 */
	public void uploadFile(MultipartFile file) {
		//		ServerBizException.checkResourceExist(file,"请选择上传文件");
		//		ServerBizException.checkArgument(file.getSize() < 3 * 1024 * 1024,"上传文件大小不能超过3M");
		//		// 原始文件名
		//		String fileName = file.getOriginalFilename();
		//		int index = fileName.lastIndexOf(".");
		//		ServerBizException.checkArgument(index != -1,"没有后缀名");
		//		// 扩展名 .pdf
		//		String extendName = fileName.substring(index);
		//		ServerBizException.checkArgument(StringUtils.equals(extendName,".pdf"),"上传文件类型有误");
		//		// 数据库文件名不能相同
		//		PdfTemplate pdfTemplateDb = pdfTemplateDao.getByName(fileName);
		//		if (Objects.nonNull(pdfTemplateDb)) {
		//			throw new ServerBizException(ErrorResponse.create(1000111,"文件名重名"));
		//		}
		//
		//		String serverDirectoryPath = linuxPath;
		//		if(StringUtils.equals(env,"dev")){
		//			//dev环境用windows
		//			serverDirectoryPath = windowPath;
		//		}
		//		// 目录
		//		File dictionary = new File(serverDirectoryPath);
		//		if (!dictionary.exists()) {
		//			dictionary.mkdirs();
		//		}
		//		// 文件
		//		String serverFilePath = serverDirectoryPath + File.separator + DateTimeUtils.getYyyyMmDdHhMmSs(new Date())+".pdf";
		//
		//		//上传文件
		//		try {
		//			ByteSink byteSink = Files.asByteSink(new File(serverFilePath));
		//			byteSink.writeFrom(new BufferedInputStream(file.getInputStream()));
		//		} catch (IOException e) {
		//			throw new ServerBizException(ErrorResponse.create(1000112,"上传文件失败"));
		//		}
		//		// 入库
		//		PdfTemplate pdfTemplate = PdfTemplate.builder()
		//				.name(fileName)
		//				.status(PdfTemplate.Status.STATUS_ON)
		//				.viewLocation(serverFilePath)
		//				.build();
		//		pdfTemplateDao.insert(pdfTemplate);
	}
}
