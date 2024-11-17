// Java Program to Illustrate DemoController File 

// Importing package in this code module 
package com.group.an.reportService;
// Importing required classes 
import com.group.an.dataService.models.*;
import com.group.an.dataService.repositories.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Annotation 
@RestController
@RequestMapping("/reports")
@SecurityRequirement(name = "jwtAuth")
// Main class 
public class ReportController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestaurantOwnerRepository restaurantOwnerRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private DeliveryPersonnelRepository deliveryPersonnelRepository;

	@GetMapping("/popular-restaurants")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "API for Admins Only")
	@Operation(summary = "Fetch popular restaurants", description = "Retrieve the data of popular restaurants", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all the restaurants"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
	})
	public ResponseEntity<List<Restaurant>> getPopularRestaurants() {
		List<Restaurant> allRestaurants = restaurantRepository.findTop5ByOrderByUserRatingsDesc();
		return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
	}

	@GetMapping("/delivery-time")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "API for Admins Only")
	@Operation(summary = "Fetch the average delivery time", description = "Retrieve the delivery time", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all the restaurants"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
	})
	public ResponseEntity<Long> getAverageDeliveryTime() {
		List<Order> allOrders = orderRepository.findAll();
		Duration duration = Duration.ZERO;
		Duration avgDuration = Duration.ZERO;
		for(Order order : allOrders) {
			if((order.getOrderedAt() != null) && (order.getDeliveredAt() !=null)) {
				duration = duration.plus(Duration.between(order.getOrderedAt(), order.getDeliveredAt()));
			}
		}
		avgDuration = duration.dividedBy(allOrders.size());

		return new ResponseEntity<>(avgDuration.getSeconds(), HttpStatus.OK);
	}

	@GetMapping("/order-trends")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "API for Admins Only")
	@Operation(summary = "Fetch order trends", description = "Retrieve the data for order trends", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all the restaurants"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
	})
	public ResponseEntity<OrderTrend> getOrderTrends(@RequestParam(name = "startDate") LocalDateTime startDate,
														   @RequestParam(name = "endDate") LocalDateTime endDate) {
		OrderTrend orderTrend = new OrderTrend();
		List<Order> allOrders = orderRepository.findAll();
		int orderCount = 0;
		Double totalRevenue = 0.0;
		Double avgOrderValue = 0.0;
		for(Order order : allOrders) {
			LocalDateTime orderDate = order.getOrderedAt();
			if((orderDate.isEqual(startDate) || orderDate.isAfter(startDate)) &&
					(orderDate.isEqual(endDate) || orderDate.isBefore(endDate))) {
				orderCount ++;
				totalRevenue += order.getPrice();
			}
		}
		avgOrderValue = totalRevenue/orderCount;

		orderTrend.setOrderCount(orderCount);
		orderTrend.setTotalRevenue(totalRevenue);
		orderTrend.setAverageOrderValue(avgOrderValue);
		return new ResponseEntity<>(orderTrend, HttpStatus.OK);
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "API for Admins Only")
	@Operation(summary = "Fetch order by delivery status & order status", description = "Retrieve the data for order delivery status", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all the restaurants"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
	})
	public ResponseEntity<List<Order>> getOrdersByDeliveryStatus(@RequestParam(name = "delivery-status", required = false) String deliveryStatus,
																 @RequestParam(name = "order-status", required = false) String orderStatus) {
		List<Order> allOrders = new ArrayList<>();
		if(null != deliveryStatus)
			allOrders = orderRepository.findByDeliveryStatus(DeliveryStatus.valueOf(deliveryStatus));
		else if(null != orderStatus)
			allOrders = orderRepository.findByOrderStatus(OrderStatus.valueOf(orderStatus));
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
	}

	@GetMapping("/health")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "API for Admins Only")
	@Operation(summary = "Fetch health status", description = "Retrieve the data for health", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all the restaurants"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
	})
	public ResponseEntity<String> getAppHealth() {
		return new ResponseEntity<>("UP", HttpStatus.OK);
	}

	@GetMapping("/active-users")
	@PreAuthorize("hasRole('ADMIN')")
	@Tag(name = "API for Admins Only")
	@Operation(summary = "Fetch count of active users", description = "Retrieve the stats of all active users", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all active users"),
			@ApiResponse(responseCode = "401", description = "Unauthorized"),
			@ApiResponse(responseCode = "500", description = "An error occurred while processing the request at the server side")
	})
	public ResponseEntity<Map<String,Integer>> getActiveUsers() {
		List<Customer> customers = customerRepository.findByIsActive(true);
		List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findByIsActive(true);
		List<DeliveryPersonnel> deliveryPersonnels = deliveryPersonnelRepository.findByIsActive(true);
		Map<String,Integer> result = new HashMap<>();
		result.put("Customers",customers.size());
		result.put("Restaurant Owners",restaurantOwners.size());
		result.put("Delivery Personnel",deliveryPersonnels.size());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
