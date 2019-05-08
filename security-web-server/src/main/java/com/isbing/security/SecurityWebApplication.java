package com.isbing.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by song bing
 * Created time 2019/5/6 13:24
 */
@SpringBootApplication(scanBasePackages = { "com.isbing" })
@MapperScan({ "com.isbing.security", "com.isbing.other" })
public class SecurityWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityWebApplication.class, args);
	}

}
