package com.group.an.reportService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.group.an.dataService","com.group.an.restaurantService","com.group.an.authService","com.group.an.reportService"})
@EnableMongoRepositories(basePackages = "com.group.an.dataService.repositories")
public class ReportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportServiceApplication.class, args);
	}

}
