package com.group.an.orderService.service;

import com.group.an.authService.security.JwtTokenUtil;
import com.group.an.dataService.models.*;
import com.group.an.dataService.repositories.OrderRepository;
import com.group.an.dataService.repositories.RestaurantRepository;
import com.group.an.orderService.requestModel.OrderRequest;
import com.group.an.orderService.responseModel.OrderAndDeliveryStatusResponse;
import com.group.an.orderService.exception.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static com.group.an.dataService.models.DeliveryStatus.ACCEPTED;
import static com.group.an.dataService.models.DeliveryStatus.DELIVERED;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Order placeOrder(OrderRequest orderRequest) {

        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if (role.equals(Role.CUSTOMER) && !userId.equals(orderRequest.getCustomerId())) {
            throw new AccessDeniedException("You are not authorized to create this resource. You can place orders only for Yourself");
        }

        Order order = new Order();

        //Generating Random Integer Id
        order.setOrderId(generateOrderId());

        //Setting from Customer Request
        order.setCustomerId(orderRequest.getCustomerId());
        order.setRestaurantId(orderRequest.getRestaurantId());
        order.setPrice(orderRequest.getPrice());
        order.setOrderItems(orderRequest.getOrderItems());

        //Setting Default Values
        order.setOrderStatus(OrderStatus.PLACED);
        order.setDeliveryStatus(DeliveryStatus.PENDING);
        order.setOrderedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    private int generateOrderId() {
        // Generate a random number between 101 and 500 (inclusive)
        Random random = new Random();
        int min = 101;
        int max = 500;
        int randomNumber = random.nextInt(max - min + 1) + min;
        if (orderRepository.findByOrderId(randomNumber) != null) {
            return generateOrderId();
        }
        return randomNumber;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomerId(int customerId) {
        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if (role.equals(Role.CUSTOMER) && !userId.equals(customerId)) {
            throw new AccessDeniedException("You are not authorized to view this resource. You can see only Your Orders");
        }

        return orderRepository.findByCustomerId(customerId);
    }

    public OrderAndDeliveryStatusResponse getOrderAndDeliveryStatus(int orderId) {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }

        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if (role.equals(Role.CUSTOMER) && !userId.equals(order.getCustomerId())) {
            throw new AccessDeniedException("You are not authorized to view this resource. You can see the status of only Your Orders");
        }

        return new OrderAndDeliveryStatusResponse(order.getOrderStatus(), order.getDeliveryStatus());
    }

    public List<Order> getOrdersByRestaurantId(int restaurantId) {

        Restaurant restaurant = restaurantRepository.findByRestaurantId(restaurantId);

        if (restaurant == null) {
            throw new ResourceNotFoundException("Restaurant not found with id: " + restaurantId);
        }

        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if (role.equals(Role.RESTAURANT_OWNER) && !userId.equals(restaurant.getOwnerId())) {
            throw new AccessDeniedException("You are not authorized to view this resource. You can see the orders of only Your Restaurant");
        }

        return orderRepository.findByRestaurantId(restaurantId);
    }

    public List<Order> getOrdersByDeliveryStatus(DeliveryStatus status) {
        return orderRepository.findByDeliveryStatus(status);
    }

    public Order updateOrderStatus(int orderId, OrderStatus status) {
        Order order = orderRepository.findByOrderId(orderId);

        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }

        Restaurant restaurant = restaurantRepository.findByRestaurantId(order.getRestaurantId());

        if (restaurant == null) {
            throw new ResourceNotFoundException("Order with orderId " + orderId + " is from Restaurant with id " + order.getRestaurantId() + ". But, Restaurant not found with id: " + order.getRestaurantId());
        }

        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if (role.equals(Role.RESTAURANT_OWNER) && !userId.equals(restaurant.getOwnerId())) {
            throw new AccessDeniedException("You are not authorized to modify this resource. You can update the OrderStatus of only Your Restaurant's Orders");
        }

        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    public Order updateDeliveryStatus(int orderId, int personnelId, DeliveryStatus status) {
        Order order = orderRepository.findByOrderId(orderId);

        if (order == null) {
            throw new ResourceNotFoundException("Order not found with id: " + orderId);
        }

        Integer userId = JwtTokenUtil.getUserIdFromAuthContext();
        Role role = JwtTokenUtil.getRoleFromAuthContext();
        if (role.equals(Role.DELIVERY_PERSONNEL) && order.getPersonnelId() != null && !userId.equals(order.getPersonnelId())) {
            throw new AccessDeniedException("You are not authorized to modify this resource. You can update the DeliveryStatus of only Your Orders");
        }

        if (ACCEPTED.equals(status)) {
            order.setPersonnelId(personnelId);
        }

        if (DELIVERED.equals(status)) {
            order.setDeliveredAt(LocalDateTime.now());
        }

        order.setDeliveryStatus(status);

        return orderRepository.save(order);
    }
}
