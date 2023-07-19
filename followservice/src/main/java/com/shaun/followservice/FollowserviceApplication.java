package com.shaun.followservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FollowserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FollowserviceApplication.class, args);
	}

}
