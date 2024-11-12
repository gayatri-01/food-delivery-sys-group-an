// Java Program to Illustrate DemoController File 

// Importing package in this code module 
package com.group.an.reportService;
// Importing required classes 
import com.group.an.dataService.models.DeliveryStatus;
import com.group.an.dataService.models.Order;
import com.group.an.dataService.models.OrderStatus;
import com.group.an.dataService.models.Restaurant;
import com.group.an.dataService.repositories.OrderRepository;
import com.group.an.dataService.repositories.RestaurantRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// Annotation 
@RestController
@RequestMapping("/reports")
// Main class 
public class ReportController {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private OrderRepository orderRepository;

	@GetMapping("/popular-restaurants")
	public ResponseEntity<List<Restaurant>> getPopularRestaurants() {
		List<Restaurant> allRestaurants = restaurantRepository.findAll();
		return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
	}

	@GetMapping("/delivery-time")
	public ResponseEntity<Double> getAverageDeliveryTime() {
		List<Order> allRestaurants = orderRepository.findAll();
		return new ResponseEntity<>(2.0, HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<Order>> getOrdersByDeliveryStatus(@RequestParam(name = "delivery-status", required = false) String deliveryStatus,
																 @RequestParam(name = "order-status", required = false) String orderStatus) {
		List<Order> allOrders = new ArrayList<>();
		if(null != deliveryStatus)
			allOrders = orderRepository.findByDeliveryStatus(DeliveryStatus.valueOf(deliveryStatus));
		else if(null != orderStatus)
			allOrders = orderRepository.findByOrderStatus(OrderStatus.valueOf(orderStatus));
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
	}
}
