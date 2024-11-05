package com.group.an.orderService.service;

import com.group.an.authService.security.JwtTokenUtil;
import com.group.an.dataService.models.*;
import com.group.an.dataService.repositories.OrderRepository;
import com.group.an.dataService.repositories.RestaurantRepository;
import com.group.an.orderService.requestModel.OrderRequest;
import com.group.an.orderService.responseModel.OrderAndDeliveryStatusResponse;
import com.group.an.orderService.exception.ResourceNotFoundException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private OrderService orderService;

    Order order;

    Restaurant restaurant;

    List<Order> orders;

    private MockedStatic<JwtTokenUtil> mockedAuthUtils;

    @AfterEach
    public void tearDown() {
        mockedAuthUtils.close();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedAuthUtils = mockStatic(JwtTokenUtil.class);
        order = new Order(1, 1, 1, 1, LocalDateTime.now(), null, 100.0, null, null, List.of(new OrderItem(1, 1, 1, 100)));
        orders = List.of(order);
        restaurant = new Restaurant(1, 1, "ABC", "XYZ", 123, LocalDateTime.now(), LocalDateTime.now(), 5, Collections.emptyList());
        when(restaurantRepository.findByRestaurantId(1)).thenReturn(restaurant);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderRepository.findAll()).thenReturn(orders);
        when(orderRepository.findByCustomerId(1)).thenReturn(orders);
        when(orderRepository.findByOrderId(1)).thenReturn(order);
        when(orderRepository.findByRestaurantId(1)).thenReturn(orders);
        when(orderRepository.findByDeliveryStatus(DeliveryStatus.PENDING)).thenReturn(orders);
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        when(orderRepository.findById(2)).thenReturn(Optional.empty());
    }

    @Test
    void testPlaceOrder() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.CUSTOMER);

        Order placedOrder = orderService.placeOrder(new OrderRequest(1, 1, 100.0, List.of(new OrderItem(1, 1, 1, 100))));
        assertEquals(order, placedOrder);
    }

    @Test
    void testGetAllOrders() {
        List<Order> result = orderService.getAllOrders();
        assertEquals(orders, result);
    }

    @Test
    void testGetOrdersByCustomerId() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.CUSTOMER);

        List<Order> result = orderService.getOrdersByCustomerId(1);
        assertEquals(orders, result);
    }

    @Test
    void testGetOrderAndDeliveryStatus() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.CUSTOMER);

        OrderAndDeliveryStatusResponse result = orderService.getOrderAndDeliveryStatus(1);
        assertEquals(order.getOrderStatus(), result.getOrderStatus());
        assertEquals(order.getDeliveryStatus(), result.getDeliveryStatus());
    }

    @Test
    void testGetOrdersByRestaurantId() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.RESTAURANT_OWNER);

        List<Order> result = orderService.getOrdersByRestaurantId(1);
        assertEquals(orders, result);
    }

    @Test
    void testGetOrdersByDeliveryStatus() {
        List<Order> result = orderService.getOrdersByDeliveryStatus(DeliveryStatus.PENDING);
        assertEquals(orders, result);
    }

    @Test
    void testUpdateOrderStatus() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.RESTAURANT_OWNER);

        Order updatedOrder = orderService.updateOrderStatus(1, OrderStatus.ACCEPTED);
        assertEquals(OrderStatus.ACCEPTED, updatedOrder.getOrderStatus());
    }

    @Test
    void testUpdateOrderStatusOrderNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrderStatus(2, OrderStatus.ACCEPTED));
    }

    @Test
    void testUpdateDeliveryStatusDelivered() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.DELIVERY_PERSONNEL);

        Order updatedOrder = orderService.updateDeliveryStatus(1, 1,DeliveryStatus.DELIVERED);
        assertEquals(DeliveryStatus.DELIVERED, updatedOrder.getDeliveryStatus());
        assertNotNull(updatedOrder.getDeliveredAt());
    }

    @Test
    void testUpdateDeliveryStatusEnroute() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.DELIVERY_PERSONNEL);

        Order updatedOrder = orderService.updateDeliveryStatus(1, 1,DeliveryStatus.ENROUTE);
        assertEquals(DeliveryStatus.ENROUTE, updatedOrder.getDeliveryStatus());
        assertNull(updatedOrder.getDeliveredAt());
    }

    @Test
    void testUpdateDeliveryStatusOrderNotFound() {
        assertThrows(ResourceNotFoundException.class, () -> orderService.updateDeliveryStatus(2, 2,DeliveryStatus.DELIVERED));
    }
}