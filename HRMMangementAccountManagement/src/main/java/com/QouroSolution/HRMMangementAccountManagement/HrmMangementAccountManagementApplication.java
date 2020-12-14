package com.QouroSolution.HRMMangementAccountManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HrmMangementAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmMangementAccountManagementApplication.class, args);
	}

}
