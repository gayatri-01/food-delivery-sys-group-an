package com.group.an.reportService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SecurityScheme(name = "jwtAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication(scanBasePackages = {"com.group.an.dataService","com.group.an.restaurantService","com.group.an.authService","com.group.an.reportService"})
//@EnableMongoRepositories(basePackages = "com.group.an.dataService.repositories")
public class ReportServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReportServiceApplication.class, args);
	}
	@Bean
	public OpenAPI customOpenAPIForCustomers(){
		return new OpenAPI()
				.info(new Info()
						.title("CustomerService API Documentation")
						.version("1.0")
						.description("APIs for Managing Customers in the Food Delivery System"));
	}
}


