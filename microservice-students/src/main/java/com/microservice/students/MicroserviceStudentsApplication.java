package com.microservice.students;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.microservice.students.client")
public class MicroserviceStudentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceStudentsApplication.class, args);
	}

}
