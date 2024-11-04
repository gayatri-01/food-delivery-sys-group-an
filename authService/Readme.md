# AuthService

## Overview
The `AuthService` provides APIs for managing security in the Food Delivery System. It can be used as a microservice or as a library to add security to your endpoints.

## Usage as a Microservice
The service exposes APIs for managing security in the Food Delivery System.

Access the Swagger documentation at: [SwaggerUI](http://localhost:43000/swagger-ui/index.html)

## Usage as a Library
To add security to your endpoints in your microservice, follow these steps:

### 1. Add the following dependency in your `pom.xml` file:
```xml
<dependency>
    <groupId>com.group.an</groupId>
    <artifactId>authService</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. Add Security Annotation & Package Scanning in your Main Class. Example:

```java
@SecurityScheme(name = "jwtAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication(scanBasePackages = {"com.group.an.dataService","com.group.an.orderService","com.group.an.authService"})
public class OrderServiceApplication {
.....
}
```
### 3. Add below annotation on the endpoints you need to secure
```java
@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
```
Refer: OrderController.java class

### 4. To access the userId & role in any of your Service Class, for any further validations, below methods can be used

```java
Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
Role role = JwtTokenUtil.getRoleFromAuthContext();
```

