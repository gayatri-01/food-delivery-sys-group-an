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

import java.time.Duration;
import java.time.LocalDateTime;
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
		List<Restaurant> allRestaurants = restaurantRepository.findTop5ByOrderByUserRatingsDesc();
		return new ResponseEntity<>(allRestaurants, HttpStatus.OK);
	}

	@GetMapping("/delivery-time")
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
	public ResponseEntity<String> getAppHealth() {
		return new ResponseEntity<>("UP", HttpStatus.OK);
	}
}
