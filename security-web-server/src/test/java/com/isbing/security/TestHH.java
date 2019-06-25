package com.isbing.security;

import com.isbing.other.bean.UserInfo;
import com.isbing.other.utils.PdfUtils;
import com.isbing.security.bean.CurrentUser;
import com.isbing.security.common.util.JsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by song bing
 * Created time 2019/5/6 16:57
 */
@TestPropertySource("classpath:/env/test/application.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { SecurityWebApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestHH {

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Test
	public void tt() {
		String s = stringRedisTemplate.opsForValue().get("token:3ae3af148687428098e850b2bb5eb065");
		System.out.println(s);
		CurrentUser currentUser = JsonUtil.toObject(s, CurrentUser.class);

		System.out.println(JsonUtil.toJson(currentUser));
	}

	@Test
	public void fillDataToFileTest() throws IOException {
		UserInfo userInfo = UserInfo.builder()
				.name("isbing")
				.age(24)
				.sex("man")
				.love("coding")
				.job("programmer")
				.mobile("1761011")
				.build();
		String from = "D:\\pdf\\tpl\\test.pdf";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = "test_" + df.format(new Date()) + ".pdf";
		String out = "D:\\pdf" + File.separator + fileName;
		PdfUtils.fillDataToFile(from, out, userInfo);
	}

}
