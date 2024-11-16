package com.group.an.customerService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories; 

@SecurityScheme(name = "jwtAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication(scanBasePackages = {"com.group.an.dataService","com.group.an.authService","com.group.an.customerService"})
//@EnableMongoRepositories(basePackages = "com.group.an.dataService.repositories")
//@EnableJpaRepositories
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
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
