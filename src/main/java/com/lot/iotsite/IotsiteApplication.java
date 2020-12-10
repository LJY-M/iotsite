package com.lot.iotsite;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;



@EnableFeignClients
@MapperScan({"com.lot.iotsite.mapper"})
@SpringBootApplication
public class IotsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotsiteApplication.class, args);
	}

}
