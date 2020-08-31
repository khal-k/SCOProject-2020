package com.atguigu.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CrowdRedis3000 {
	public static void main(String[] args) {
		SpringApplication.run(CrowdRedis3000.class, args);
	}
}
