package com.group.an.restaurantService;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.group.an.dataService","com.group.an.restaurantService","com.group.an.authService"})
@EnableMongoRepositories(basePackages = "com.group.an.dataService.repositories")
@SecurityScheme(name = "jwtAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class RestaurantServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantServiceApplication.class, args);
	}
	@Bean
	public OpenAPI customOpenAPIForRestaurant() {
		return new OpenAPI()
				.info(new Info()
						.title("RestaurantService API Documentation")
						.version("1.0")
						.description("APIs for Managing Restaurants in the Food Delivery System"));
	}

}
