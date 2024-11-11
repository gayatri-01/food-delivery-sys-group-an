package com.group.an.orderService.controller;

import com.group.an.dataService.models.DeliveryStatus;
import com.group.an.dataService.models.Order;
import com.group.an.dataService.models.OrderItem;
import com.group.an.dataService.models.OrderStatus;
import com.group.an.orderService.requestModel.DeliveryStatusUpdateRequest;
import com.group.an.orderService.requestModel.OrderRequest;
import com.group.an.orderService.requestModel.OrderStatusUpdateRequest;
import com.group.an.orderService.responseModel.OrderAndDeliveryStatusResponse;
import com.group.an.orderService.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    Order order;

    List<Order> orders;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order(1, 1, 1, 1, LocalDateTime.now(), null, 100.0, OrderStatus.ACCEPTED, null, List.of(new OrderItem(1, 1, 1, 100)));
        orders = Arrays.asList(order);
        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(order);
        when(orderService.getAllOrders()).thenReturn(orders);
        when(orderService.getOrdersByCustomerId(1)).thenReturn(orders);
        when(orderService.getOrderAndDeliveryStatus(1)).thenReturn(new OrderAndDeliveryStatusResponse(order.getOrderStatus(),order.getDeliveryStatus()));
        when(orderService.getOrdersByRestaurantId(1)).thenReturn(orders);
        when(orderService.getOrdersByDeliveryStatus(DeliveryStatus.PENDING)).thenReturn(orders);
        when(orderService.updateOrderStatus(1, OrderStatus.PREPARING)).thenReturn(order);
        when(orderService.updateDeliveryStatus(1, 1, DeliveryStatus.DELIVERED)).thenReturn(order);
    }

    @Test
    void testPlaceOrder() {
        ResponseEntity<Order> response = orderController.placeOrder(new OrderRequest(1, 1, 100.0, List.of(new OrderItem(1, 1, 1, 100))));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    void testGetAllOrders() {
        ResponseEntity<List<Order>> response = orderController.getAllOrders();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void testGetOrdersByCustomerId() {
        ResponseEntity<List<Order>> response = orderController.getOrdersByCustomerId(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void testGetOrderAndDeliveryStatus() {
        ResponseEntity<OrderAndDeliveryStatusResponse> response = orderController.getOrderAndDeliveryStatus(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order.getOrderStatus(), response.getBody().getOrderStatus());
        assertEquals(order.getDeliveryStatus(), response.getBody().getDeliveryStatus());
    }

    @Test
    void testGetOrdersByRestaurantId() {
        ResponseEntity<List<Order>> response = orderController.getOrdersByRestaurantId(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void testGetOrdersByDeliveryStatus() {
        ResponseEntity<List<Order>> response = orderController.getOrdersByDeliveryStatus(DeliveryStatus.PENDING);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
    }

    @Test
    void testUpdateOrderStatus() {
        ResponseEntity<Order> response = orderController.updateOrderStatus(new OrderStatusUpdateRequest(1, OrderStatus.PREPARING));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }

    @Test
    void testUpdateDeliveryStatus() {
        ResponseEntity<Order> response = orderController.updateDeliveryStatus(new DeliveryStatusUpdateRequest(1, 1,DeliveryStatus.DELIVERED));
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(order, response.getBody());
    }
}