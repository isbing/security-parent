package com.isbing.security;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

/**
 * Created by song bing
 * Created time 2019/5/6 13:24
 */
@SpringBootApplication(scanBasePackages = { "com.isbing" })
@MapperScan({ "com.isbing.security", "com.isbing.other" })
public class SecurityWebApplication {

	public static void main(String[] args) {
		long currentTime = System.currentTimeMillis() - 30 * 60 * 1000;
		Date endTime = new Date(currentTime);
		System.out.println(endTime);
		SpringApplication.run(SecurityWebApplication.class, args);
	}

}
