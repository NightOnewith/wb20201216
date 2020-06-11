package com.ethan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ethan.ryds.dao")
public class RydsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RydsApplication.class, args);
	}

}
