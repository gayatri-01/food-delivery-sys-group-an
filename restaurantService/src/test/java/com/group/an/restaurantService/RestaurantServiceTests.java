package com.group.an.restaurantService;

import com.group.an.authService.security.JwtTokenUtil;
import com.group.an.dataService.models.*;
import com.group.an.dataService.repositories.OrderRepository;
import com.group.an.dataService.repositories.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class RestaurantServiceTests {
    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    Restaurant restaurant, restaurantWithItem, restaurant2WithItem, restaurant2WithUpdatedItem;

    MenuItem menuItem, updatedMenuItem;

    List<Restaurant> restaurants;

    private MockedStatic<JwtTokenUtil> mockedAuthUtils;

    @AfterEach
    public void tearDown() {
        mockedAuthUtils.close();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockedAuthUtils = mockStatic(JwtTokenUtil.class);
        menuItem = new MenuItem(1, "Veg Sandwich", "Veg Sandwich is Veg Sandwich", 130, true);
        updatedMenuItem = new MenuItem(1, "Veg Cheese Sandwich", "Veg Cheese Sandwich is not Veg Sandwich", 170, true);
        restaurant = new Restaurant(1, 1, "ABC", "XYZ", 123, LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), 5, new ArrayList<>());
        restaurantWithItem = new Restaurant(1, 1, "ABC", "XYZ", 123, LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), 5, new ArrayList<>() {{add(menuItem);} });
        restaurant2WithItem = new Restaurant(2, 1, "ABC", "XYZ", 123, LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), 5, new ArrayList<>() {{add(menuItem);} });
        restaurant2WithUpdatedItem = new Restaurant(2, 1, "ABC", "XYZ", 123, LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), LocalDateTime.of(LocalDate.of(2024,11,10), LocalTime.of(18,30,0)), 5, new ArrayList<>() {{add(updatedMenuItem);} });
        when(restaurantRepository.findById(1)).thenReturn(Optional.ofNullable(restaurant));
        when(restaurantRepository.findById(2)).thenReturn(Optional.ofNullable(restaurant2WithItem));
        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurantWithItem)).thenReturn(restaurantWithItem);
        when(restaurantRepository.save(restaurant2WithUpdatedItem)).thenReturn(restaurant2WithUpdatedItem);
        when(restaurantRepository.findAll()).thenReturn(restaurants);
    }

    @Test
    void testAddRestaurant() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.RESTAURANT_OWNER);

        Restaurant addedRestaurant = restaurantService.addRestaurant(restaurant);
        assertEquals(restaurant, addedRestaurant);
    }

    @Test
    void testGetAllRestaurants() {
        List<Restaurant> result = restaurantService.getAllRestaurants();
        assertEquals(restaurants, result);
    }

    @Test
    void testAddItemToMenuOfRestaurant() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.RESTAURANT_OWNER);

        Restaurant result = restaurantService.addItemToMenuOfRestaurant(menuItem,1);
        assertEquals(restaurantWithItem.getMenus().size(), result.getMenus().size());
    }

    @Test
    void testUpdateMenuItemOfRestaurant() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.RESTAURANT_OWNER);

        Restaurant result = restaurantService.updateMenuItemOfRestaurant(updatedMenuItem, 2, 1);
        assertEquals(restaurant2WithUpdatedItem.getMenus().get(0).getItemName(), result.getMenus().get(0).getItemName());
    }

    @Test
    void testDeleteMenuItemOfRestaurant() {
        mockedAuthUtils.when(JwtTokenUtil::getUserIdFromAuthContext).thenReturn(1);
        mockedAuthUtils.when(JwtTokenUtil::getRoleFromAuthContext).thenReturn(Role.RESTAURANT_OWNER);

        Restaurant result = restaurantService.deleteMenuItemOfRestaurant(1, 1);
        assertEquals(restaurant, result);
    }
}
