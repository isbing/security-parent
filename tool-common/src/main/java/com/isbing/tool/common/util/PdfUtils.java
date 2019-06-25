//
//
//import com.google.common.collect.Maps;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.pdf.*;
//import com.lowagie.text.Document;
//import com.lowagie.text.DocumentException;
//import com.lowagie.text.Element;
//import com.lowagie.text.Font;
//import com.lowagie.text.Rectangle;
//import com.lowagie.text.pdf.*;
//
//import org.apache.commons.lang3.StringUtils;
//
//import javax.servlet.http.HttpServletResponse;
//import java.awt.*;
//import java.io.*;
//import java.math.BigDecimal;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Map;
//
///**
// * Created by song bing
// * Created time 2019/4/8 15:48
// */
//public class PdfUtils {
//	private static final String SERVERPATH = "C:\\contract\\haha";
//
//	private static final int SELECT_1 = 1;//非正式文件
//	private static final int SELECT_2 = 2;//纵横文学
//
//	/**
//	 * 生成新的文件
//	 * @param contractTemplate
//	 * @param contract
//	 * @param authorId
//	 * @return
//	 */
//	public static String fillDataToFile(ContractTemplate contractTemplate, Contract contract, Long authorId) {
//		// 目录 /xxx/authorId/bookId
//		String dir = SERVERPATH + File.separator + authorId + File.separator + contract.getBookId();
//		File dictionary = new File(dir);
//		if (!dictionary.exists()) {
//			dictionary.mkdirs();
//		}
//		// 文件 /xxx/authorId/bookId/bookId_payType_时间戳.pdf
//		String fileName = String.format("%s_%s_%s.pdf", contract.getBookId(), contractTemplate.getPayType(),
//				DateTimeUtils.getYyyyMmDdHhMmSs(new Date()));
//		String serverFilePath = dir + File.separator + fileName;
//
//		try {
//			doCommon(contractTemplate.getViewLocation(), new FileOutputStream(serverFilePath), contract,
//					contractTemplate.getPayType(), 1, PdfUtils.SELECT_2);
//		} catch (IOException e) {
//			logger.info(e.getMessage());
//			return null;
//		}
//		return serverFilePath;
//	}
//
//	/**
//	 * 将文件写入到页面
//	 * @param contractTemplate
//	 * @param response
//	 * @param contract
//	 * @param type
//	 */
//	public static void fillDataToHtml(ContractTemplate contractTemplate, Contract contract,
//			HttpServletResponse response, int type) {
//		try {
//			doCommon(contractTemplate.getViewLocation(), response.getOutputStream(), contract,
//					contractTemplate.getPayType(), type, PdfUtils.SELECT_1);
//		} catch (IOException e) {
//			logger.info(e.getMessage());
//		}
//	}
//
//
//	private static void doCommon(String from, OutputStream out, Map<String, Object> data, Integer type) {
//		PdfReader reader;
//		ByteArrayOutputStream bos;
//		PdfStamper stamper;
//		Document doc = null;
//		try {
//			reader = new PdfReader(from);//读取pdf模板
//			bos = new ByteArrayOutputStream();
//			stamper = new PdfStamper(reader, bos);
//			AcroFields form = stamper.getAcroFields();
//
//			// 遍历data 给pdf表单表格赋值
//			for (String key : data.keySet()) {
//				if (data.get(key) != null) {
//					form.setField(key, data.get(key).toString());
//				}
//			}
//
//			// 增加图片水印
//			BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
//			PdfGState gs = new PdfGState();
//			int total = reader.getNumberOfPages() + 1;
//			for (int i = 1; i < total; i++) {
//				PdfContentByte content = stamper.getOverContent(i);
//				gs.setFillOpacity(0.8f);
//				content.setGState(gs);
//				content.beginText();
//				content.setColorFill(Color.LIGHT_GRAY);
//				content.setFontAndSize(base, 40);
//				content.setTextMatrix(70, 200);
//
//				Rectangle pageRect = stamper.getReader().getPageSizeWithRotation(i);
//				// 计算水印X,Y坐标
//				float x = pageRect.getWidth() / 2;
//				float y = pageRect.getHeight() / 2;
//
//				if (select == PdfUtils.SELECT_2) {
//					for (int j = 160; j <= 160; j += 80) {
//						content.showTextAligned(Element.ALIGN_CENTER, "纵横文学", x, y - j, 40);
//						content.showTextAligned(Element.ALIGN_CENTER, "纵横文学", x, y + j, 40);
//					}
//				}
//				if (select == PdfUtils.SELECT_1) {
//					content.showTextAligned(Element.ALIGN_CENTER, "非正式文件,请勿下载打印！", x, y, 40);
//					for (int j = 80; j <= 160; j += 80) {
//						content.showTextAligned(Element.ALIGN_CENTER, "非正式文件,请勿下载打印！", x, y - j, 40);
//						content.showTextAligned(Element.ALIGN_CENTER, "非正式文件,请勿下载打印！", x, y + j, 40);
//					}
//				}
//				content.endText();
//			}
//
//			stamper.setFormFlattening(true);
//			stamper.close();
//			doc = new Document();
//			PdfCopy copy = new PdfCopy(doc, out);
//
//			// 设置文件为只读模式
//			String md5 = Md5Util.getMD5WithSalt(contract.getBookId() + "");
//			if (StringUtils.isEmpty(md5)) {
//				md5 = "ZHzonghengZH";
//			}
//			copy.setEncryption(null, md5.getBytes(), PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
//
//			doc.open();
//			// 获取总页数
//			int pages = reader.getNumberOfPages();
//			// 这一句话 一定要在循环外面。否则生成的文件会是现有文件的【pages】倍
//			PdfReader pdfReader = new PdfReader(bos.toByteArray());
//			for (int i = 1; i <= pages; i++) {
//				PdfImportedPage importPage = copy.getImportedPage(pdfReader, i);
//				copy.addPage(importPage);
//			}
//		} catch (IOException e) {
//
//		} catch (BadPdfFormatException e) {
//
//		} catch (DocumentException e) {
//
//		} finally {
//			if (doc != null) {
//				doc.close();
//			}
//		}
//	}
//
//	/**
//	 * 填充作者数据
//	 * @param contract
//	 * @return
//	 */
//	private static Map<String, Object> buildAuthorInfo(Contract contract) {
//		checkContractParam(contract);
//		Map<String, Object> map = Maps.newHashMap();
//		// 作者提交信息 当前时间
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		map.put("now_y", contract == null ? "" : calendar.get(Calendar.YEAR));
//		map.put("now_m", contract == null ? "" : calendar.get(Calendar.MONTH) + 1);
//		map.put("now_d", contract == null ? "" : calendar.get(Calendar.DAY_OF_MONTH));
//		// 合同是10年 用完稿时间 加10年
//		if (contract == null) {
//			map.put("to_y", "");
//			map.put("to_m", "");
//			map.put("to_d", "");
//		} else {
//			Calendar toCalendar = Calendar.getInstance();
//			toCalendar.setTime(contract.getExpectFinishTime());
//			map.put("to_y", toCalendar.get(Calendar.YEAR) + 10);
//			map.put("to_m", toCalendar.get(Calendar.MONTH) + 1);
//			map.put("to_d", toCalendar.get(Calendar.DAY_OF_MONTH));
//		}
//
//		map.put("realName", contract == null ? "" : contract.getRealName());
//		map.put("penName", contract == null ? "" : contract.getPenName());
//		map.put("penNameV2", contract == null ? "" : contract.getPenName());
//
//		map.put("gender", contract == null ? "" : contract.getGender() == 0 ? "女" : "男");
//		map.put("address", contract == null ? "" : contract.getAddress());
//		map.put("idNum", contract == null ? "" : contract.getIdNum());
//		map.put("mobile", contract == null ? "" : contract.getMobile());
//		map.put("email", contract == null ? "" : contract.getEmail());
//		map.put("bankCardNum", contract == null ? "" : contract.getBankCardNum());
//		map.put("bankName", contract == null ? "" : contract.getBankName());
//		map.put("bankAccount", contract == null ? "" : contract.getBankAccount());
//		map.put("bankProvince", contract == null ? "" : contract.getBankProvince());
//		map.put("bankCity", contract == null ? "" : contract.getBankCity());
//		map.put("bankArea", contract == null ? "" : contract.getBankArea());
//		map.put("bookOutline", contract == null ? "" : contract.getBookOutline());
//		map.put("code", contract == null ? "" : contract.getCode());
//		map.put("qq", contract == null ? "" : contract.getQq());
//		map.put("bankInfo",
//				contract == null ? "" : String.format("%s%s", contract.getBankName(), contract.getBankArea()));
//		return map;
//	}
//
//}
