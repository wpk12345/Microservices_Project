package com.company.groupprojectconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class GroupProjectCloudConfigServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupProjectCloudConfigServiceApplication.class, args);
	}

}
