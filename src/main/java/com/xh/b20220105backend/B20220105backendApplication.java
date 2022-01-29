package com.xh.b20220105backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xh.b20220105backend.mapper")
public class B20220105backendApplication {
	public static void main(String[] args) {
		SpringApplication.run(B20220105backendApplication.class, args);
	}
}
