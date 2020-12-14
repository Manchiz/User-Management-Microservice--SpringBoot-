package com.QouroSolution.HRMMangementUserApiDiscoveryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class HrmMangementUserApiDiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmMangementUserApiDiscoveryServiceApplication.class, args);
	}

}
