package com.group.an.reportService.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

import com.group.an.reportService.ReportController;
import com.group.an.dataService.models.DeliveryStatus;
import com.group.an.dataService.models.MenuItem;
import com.group.an.dataService.models.Order;
import com.group.an.dataService.models.Restaurant;
import com.group.an.dataService.repositories.OrderRepository;
import com.group.an.dataService.repositories.RestaurantRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class ReportControllerTest {
	
	@InjectMocks
    private ReportController reportController;
	
	@Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPopularRestaurants_ReturnsListOfRestaurants() {

        List<Restaurant> mockRestaurants = new ArrayList<>();
        Restaurant restaurant = new Restaurant();
        restaurant.setName("DELHI KA DHABA");
        List<MenuItem> menus = new ArrayList<>();
        MenuItem menuItem = new MenuItem();
        menuItem.setAvailable(true);
        menuItem.setItemName("CHOLE BATURE");
        menuItem.setDescription("A popular Indian Dish liked by all");
        menuItem.setMenuItemId(7622);
        menuItem.setPrice(131.97);
        menus.add(menuItem);
        restaurant.setMenus(menus);
        restaurant.setAddress("No 16, MG road, New Delhi");
        restaurant.setOwnerId(23499);
        restaurant.setPhoneNumber(720220690);
        restaurant.setRestaurantId(1122809);
        restaurant.setUserRatings(5);
        mockRestaurants.add(restaurant);
   
        when(restaurantRepository.findTop5ByOrderByUserRatingsDesc()).thenReturn(mockRestaurants);
    
        ResponseEntity<List<Restaurant>> response = reportController.getPopularRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(restaurantRepository, times(1)).findTop5ByOrderByUserRatingsDesc();
    }

    @Test
    void testGetPopularRestaurants_ReturnsEmptyList() {
   
        when(restaurantRepository.findTop5ByOrderByUserRatingsDesc()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Restaurant>> response =reportController.getPopularRestaurants();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(restaurantRepository, times(1)).findTop5ByOrderByUserRatingsDesc();
    }

    @Test
    void testGetPopularRestaurants_ThrowsException() {
        when(restaurantRepository.findTop5ByOrderByUserRatingsDesc()).thenThrow(new RuntimeException("Database Error")); 
        try {
        	reportController.getPopularRestaurants();
        } catch (RuntimeException ex) {
            assertEquals("Database Error", ex.getMessage());
        }

        verify(restaurantRepository, times(1)).findTop5ByOrderByUserRatingsDesc();
    }
    
    @Test
    void testGetAverageDeliveryTime_ReturnsAverageTime() {
 
        List<Order> mockOrders = new ArrayList<>();
        Order objOrder = new Order();
        objOrder.setOrderId(1234);
        objOrder.setPrice(234.50);
        objOrder.setPersonnelId(1209);
        objOrder.setRestaurantId(13412);
        mockOrders.add(objOrder);

        when(orderRepository.findAll()).thenReturn(mockOrders);
        ResponseEntity<Long> response = reportController.getAverageDeliveryTime();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(5400L, response.getBody());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetAverageDeliveryTime_ReturnsZeroForNoOrders() {

        when(orderRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<Long> response = reportController.getAverageDeliveryTime();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0L, response.getBody());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetAverageDeliveryTime_HandlesOrdersWithMissingTimestamps() {
     
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(new Order());

        when(orderRepository.findAll()).thenReturn(mockOrders);

        ResponseEntity<Long> response = reportController.getAverageDeliveryTime();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1800L, response.getBody()); 
        verify(orderRepository, times(1)).findAll();
    }
    @Test
    void testGetOrdersByDeliveryStatus_BothParametersNull() {
        ResponseEntity<List<Order>> response = reportController.getOrdersByDeliveryStatus(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
        verify(orderRepository, never()).findByDeliveryStatus(any());
        verify(orderRepository, never()).findByOrderStatus(any());
    }

    @Test
    void testGetOrdersByDeliveryStatus_WithDeliveryStatus() {
  
        List<Order> mockOrders = new ArrayList<>();
        mockOrders.add(new Order());

        when(orderRepository.findByDeliveryStatus(DeliveryStatus.DELIVERED)).thenReturn(mockOrders);
        ResponseEntity<List<Order>> response = reportController.getOrdersByDeliveryStatus("DELIVERED", null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(orderRepository, times(1)).findByDeliveryStatus(DeliveryStatus.DELIVERED);
        verify(orderRepository, never()).findByOrderStatus(any());
    }

    
    @Test
    void testGetOrdersByDeliveryStatus_InvalidDeliveryStatus() {
        try {
        	reportController.getOrdersByDeliveryStatus("INVALID_STATUS", null);
        } catch (IllegalArgumentException ex) {
            assertEquals("No enum constant com.example.model.DeliveryStatus.INVALID_STATUS", ex.getMessage());
        }

        verify(orderRepository, never()).findByDeliveryStatus(any());
        verify(orderRepository, never()).findByOrderStatus(any());
    }

    @Test
    void testGetOrdersByDeliveryStatus_InvalidOrderStatus() {
        try {
        	reportController.getOrdersByDeliveryStatus(null, "INVALID_STATUS");
        } catch (IllegalArgumentException ex) {
            assertEquals("No enum constant com.example.model.OrderStatus.INVALID_STATUS", ex.getMessage());
        }

        verify(orderRepository, never()).findByDeliveryStatus(any());
        verify(orderRepository, never()).findByOrderStatus(any());
    }
    
    @Test
    void testGetAppHealth_ReturnsUpStatus() {
        
        ResponseEntity<String> response = reportController.getAppHealth();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("UP", response.getBody());
    }   
    
}

