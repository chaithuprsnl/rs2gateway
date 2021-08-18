package com.payarc.rs2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Rs2GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(Rs2GatewayApplication.class, args);
	}

}
