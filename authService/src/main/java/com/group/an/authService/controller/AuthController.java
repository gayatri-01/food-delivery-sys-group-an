package com.group.an.authService.controller;

import com.group.an.authService.requestModel.*;
import com.group.an.authService.responseModel.AuthResponse;
import com.group.an.authService.service.AuthService;
import com.group.an.dataService.models.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@Profile("auth")
@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@Tag(name = "APIs for User Registrations")
	@PostMapping("/register/customer")
	@Operation(summary = "Register customer", description = "Register a new customer",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Customer registration request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = CustomerRegisterRequest.class),
							examples = @ExampleObject(
									value = "{ \"name\": \"John Doe\", \"email\": \"john.doe@example.com\", \"password\": \"password123\", \"contactNumber\": 1234567890, \"deliveryAddress\": \"Mumbai, India\" ,   \"paymentDetail\": {\n" +
											"        \"cardType\": \"RU\",\n" +
											"        \"cardNumber\": \"HFKS67\",\n" +
											"        \"cardExpiry\": \"2024-10-12T20:30\",\n" +
											"        \"upiNumber\": 474538999,\n" +
											"        \"upiId\": \"akshay4567\"\n" +
											"    }}"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "201", description = "Customer successfully registered"),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> registerCustomer(@RequestBody CustomerRegisterRequest customerRegisterRequest) {
		AuthResponse authResponse = authService.registerCustomer(customerRegisterRequest);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@Tag(name = "APIs for User Registrations")
	@PostMapping("/register/restaurantOwner")
	@Operation(summary = "Register restaurant owner", description = "Register a new restaurant owner",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Restaurant owner registration request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = RestaurantOwnerRegisterRequest.class),
							examples = @ExampleObject(
									value = "{ \"name\": \"Jane Doe\", \"email\": \"jane.doe@example.com\", \"password\": \"password123\", \"contactNumber\": 0987654321, \"restuarantId\": 200 }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "201", description = "Restaurant owner successfully registered"),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> registerRestaurantOwner(@RequestBody RestaurantOwnerRegisterRequest restaurantOwnerRegisterRequest) {
		AuthResponse authResponse = authService.registerRestaurantOwner(restaurantOwnerRegisterRequest);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@Tag(name = "APIs for User Registrations")
	@PostMapping("/register/deliveryPersonnel")
	@Operation(summary = "Register delivery personnel", description = "Register a new delivery personnel",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Delivery personnel registration request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = DeliveryPersonnelRegisterRequest.class),
							examples = @ExampleObject(
									value = "{ \"name\": \"Sam Smith\", \"email\": \"sam.smith@example.com\", \"password\": \"password123\", \"contactNumber\": 1122334455, \"vehicleType\": \"BIKE\" }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "201", description = "Delivery personnel successfully registered"),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> registerDeliveryPersonnel(@RequestBody DeliveryPersonnelRegisterRequest deliveryPersonnelRegisterRequest) {
		AuthResponse authResponse = authService.registerDeliveryPersonnel(deliveryPersonnelRegisterRequest);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@Tag(name = "APIs for User Registrations")
	@PostMapping("/register/admin")
	@Operation(summary = "Register admin", description = "Register a new admin",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Admin registration request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = AdminRegisterRequest.class),
							examples = @ExampleObject(
									value = "{ \"name\": \"Admin User\", \"email\": \"admin@example.com\", \"password\": \"adminpassword\" , \"contactNumber\": 123456789 }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "201", description = "Admin successfully registered"),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> registerAdmin(@RequestBody AdminRegisterRequest adminRegisterRequest) {
		AuthResponse authResponse = authService.registerAdmin(adminRegisterRequest);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@Tag(name = "APIs for User Logins")
	@PostMapping("/login/customer")
	@Operation(summary = "Login customer", description = "Login a customer",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Customer login request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = LoginRequest.class),
							examples = @ExampleObject(
									value = "{ \"userId\":123, \"password\": \"password123\" }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "Customer successfully logged in"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest) {
		AuthResponse authResponse = authService.loginUser(loginRequest, Role.CUSTOMER);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@Tag(name = "APIs for User Logins")
	@PostMapping("/login/restaurantOwner")
	@Operation(summary = "Login restaurant owner", description = "Login a restaurant owner",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Restaurant owner login request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = LoginRequest.class),
							examples = @ExampleObject(
									value = "{ \"userId\":123, \"password\": \"password123\" }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "Restaurant owner successfully logged in"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> loginRestaurantOwner(@RequestBody LoginRequest loginRequest) {
		AuthResponse authResponse = authService.loginUser(loginRequest, Role.RESTAURANT_OWNER);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@Tag(name = "APIs for User Logins")
	@PostMapping("/login/deliveryPersonnel")
	@Operation(summary = "Login delivery personnel", description = "Login a delivery personnel",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Delivery personnel login request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = LoginRequest.class),
							examples = @ExampleObject(
									value = "{ \"userId\":123, \"password\": \"password123\" }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "Delivery personnel successfully logged in"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> loginDeliveryPersonnel(@RequestBody LoginRequest loginRequest) {
		AuthResponse authResponse = authService.loginUser(loginRequest, Role.DELIVERY_PERSONNEL);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@Tag(name = "APIs for User Logins")
	@PostMapping("/login/admin")
	@Operation(summary = "Login admin", description = "Login an admin",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Admin login request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = LoginRequest.class),
							examples = @ExampleObject(
									value = "{ \"userId\":123, \"password\": \"password123\" }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "Admin successfully logged in"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> loginAdmin(@RequestBody LoginRequest loginRequest) {
		AuthResponse authResponse = authService.loginUser(loginRequest, Role.ADMIN);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@PostMapping("/activate")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "APIs for Admin")
	@Operation(summary = "Activate user", description = "Activate a user",
			security = @SecurityRequirement(name = "jwtAuth"),
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "User activation request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ActivationRequest.class),
							examples = @ExampleObject(
									value = "{ \"role\": \"CUSTOMER\", \"userId\": 123 }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "User successfully activated"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> activateUser(@RequestBody ActivationRequest activationRequest) {
		AuthResponse authResponse = authService.updateActivationStatus(activationRequest, true);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@PostMapping("/deactivate")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "APIs for Admin")
	@Operation(summary = "Deactivate user", description = "Deactivate a user",
			security = @SecurityRequirement(name = "jwtAuth"),
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "User deactivation request payload",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = ActivationRequest.class),
							examples = @ExampleObject(
									value = "{ \"role\": \"CUSTOMER\", \"userId\": 123 }"
							)
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "User successfully deactivated"),
					@ApiResponse(responseCode = "401", description = "Unauthorized"),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side")
			})
	public ResponseEntity<AuthResponse> deactivateUser(@RequestBody ActivationRequest activationRequest) {
		AuthResponse authResponse = authService.updateActivationStatus(activationRequest, false);
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}

	@ExceptionHandler(Exception.class)
	@Operation(summary = "Handle exceptions", description = "Handle exceptions and return appropriate response",
			responses = {
					@ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = AuthResponse.class))),
					@ApiResponse(responseCode = "500", description = "Error occurred while Processing Request at the Server Side", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
			})
	public ResponseEntity<?> handleException(Exception exception) {
		AuthResponse authResponse = AuthResponse.builder()
				.status(exception instanceof BadCredentialsException ? HttpStatus.UNAUTHORIZED.value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message(exception.getMessage())
				.build();
		return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
	}
}