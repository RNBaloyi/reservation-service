package com.binary.microservices.reservation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.binary.microservices.reservation_service.client")
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}

}
