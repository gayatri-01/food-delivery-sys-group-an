package com.group.an.restaurantService;

import com.group.an.dataService.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RestaurantControllerTests {
    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(restaurantService.addRestaurant(any(Restaurant.class))).thenReturn(new Restaurant());
        when(restaurantService.getAllRestaurants()).thenReturn(new ArrayList<>());
        when(restaurantService.getAllRestaurant(1)).thenReturn(new ArrayList<>());
        when(restaurantService.addItemToMenuOfRestaurant(new MenuItem(), 1)).thenReturn(new Restaurant());
        when(restaurantService.updateMenuItemOfRestaurant(new MenuItem(),1, 1)).thenReturn(new Restaurant());
        when(restaurantService.deleteMenuItemOfRestaurant(1, 1)).thenReturn(new Restaurant());
    }

    @Test
    void testGetAllRestaurants() {
        ResponseEntity<List<Restaurant>> response = restaurantController.getAllRestaurants();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddRestaurant() {
        ResponseEntity<Restaurant> response = restaurantController.addRestaurant(new Restaurant());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testGetAllMenuItems() {
        ResponseEntity<List<MenuItem>> response = restaurantController.getAllRestaurant(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddItemToMenuOfRestaurant() {
        ResponseEntity<Restaurant> response = restaurantController.addItemToMenuOfRestaurant(new MenuItem(),1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateItemOfMenuOfRestaurant() {
        ResponseEntity<Restaurant> response = restaurantController.updateMenuItemOfRestaurant(new MenuItem(),1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteItemOfMenuOfRestaurant() {
        ResponseEntity<Restaurant> response = restaurantController.deleteMenuItemOfRestaurant(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
