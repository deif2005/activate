package com.order.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.order.machine.mapper")
public class ActivateServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivateServiceApplication.class, args);
	}

}
