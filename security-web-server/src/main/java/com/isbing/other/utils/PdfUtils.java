package com.isbing.other.utils;

import com.google.common.collect.Maps;
import com.isbing.other.bean.UserInfo;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.*;
import java.util.Map;

/**
 * Created by song bing
 * Created time 2019/4/8 15:48
 */
public class PdfUtils {

	public static void fillDataToFile(String from, String out, UserInfo userInfo) {
		try {
			Map<String, Object> userInfoMap = buildUserInfo(userInfo);
			doCommon(from, new FileOutputStream(out), userInfoMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fillDataToHtml(String from, HttpServletResponse response, UserInfo userInfo) {
		try {
			Map<String, Object> userInfoMap = buildUserInfo(userInfo);
			doCommon(from, response.getOutputStream(), userInfoMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void doCommon(String from, OutputStream out, Map<String, Object> data) {
		PdfReader reader;
		ByteArrayOutputStream bos;
		PdfStamper stamper;
		Document doc = null;
		try {
			reader = new PdfReader(from);//读取pdf模板
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();

			// 遍历data 给pdf表单表格赋值
			for (String key : data.keySet()) {
				if (data.get(key) != null) {
					form.setField(key, data.get(key).toString());
				}
			}

			// 增加图片水印
			BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
			PdfGState gs = new PdfGState();
			int total = reader.getNumberOfPages() + 1;
			for (int i = 1; i < total; i++) {
				PdfContentByte content = stamper.getOverContent(i);
				gs.setFillOpacity(0.8f);
				content.setGState(gs);
				content.beginText();
				content.setColorFill(Color.LIGHT_GRAY);
				content.setFontAndSize(base, 40);
				content.setTextMatrix(70, 200);
				Rectangle pageRect = stamper.getReader().getPageSizeWithRotation(i);
				// 计算水印X,Y坐标
				float x = pageRect.getWidth() / 2;
				float y = pageRect.getHeight() / 2;
				content.showTextAligned(Element.ALIGN_CENTER, "非正式文件,请勿下载打印！", x, y, 40);
				for (int j = 80; j <= 160; j += 80) {
					content.showTextAligned(Element.ALIGN_CENTER, "非正式文件,请勿下载打印！", x, y - j, 40);
					content.showTextAligned(Element.ALIGN_CENTER, "非正式文件,请勿下载打印！", x, y + j, 40);
				}
				content.endText();
			}
			stamper.setFormFlattening(true);
			stamper.close();
			doc = new Document();
			PdfCopy copy = new PdfCopy(doc, out);
			// 设置文件为只读模式
			String md5 = "test111";
			copy.setEncryption(null, md5.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
			doc.open();
			// 获取总页数
			int pages = reader.getNumberOfPages();
			// 这一句话 一定要在循环外面。否则生成的文件会是现有文件的【pages】倍
			PdfReader pdfReader = new PdfReader(bos.toByteArray());
			for (int i = 1; i <= pages; i++) {
				PdfImportedPage importPage = copy.getImportedPage(pdfReader, i);
				copy.addPage(importPage);
			}
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		} finally {
			if (doc != null) {
				doc.close();
			}
		}
	}

	/**
	 * 填充个人信息
	 * @param userInfo
	 * @return
	 */
	private static Map<String, Object> buildUserInfo(UserInfo userInfo) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("name", userInfo.getName());
		map.put("age", userInfo.getAge());
		map.put("sex", userInfo.getSex());
		map.put("love", userInfo.getLove());
		map.put("job", userInfo.getJob());
		map.put("mobile", userInfo.getMobile());
		return map;
	}

}
