package com.group.an.restaurantService;

import com.group.an.dataService.models.MenuItem;
import com.group.an.dataService.models.Restaurant;
import com.group.an.dataService.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant addRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public List<MenuItem> getAllRestaurant(Integer restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            return restaurant.get().getMenus();
        } else {
            return new ArrayList<>();
        }
    }

    public Restaurant addItemToMenuOfRestaurant(MenuItem item, Integer restaurantId) {
        // ToDo: Validate if restaurantId on the endpoint and the restaurantId of the logged in RestaurantOwner is same
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            List<MenuItem> existingMenuItems = restaurant.get().getMenus();
            if(existingMenuItems == null) {
                existingMenuItems = new ArrayList<>();
            }
            existingMenuItems.add(item);
            restaurant.get().setMenus(existingMenuItems);
            restaurantRepository.save(restaurant.get());
            return restaurant.get();
        } else {
            return new Restaurant();
        }
    }

    public Restaurant updateMenuItemOfRestaurant(MenuItem item,
                                                 Integer restaurantId,
                                                 Integer menuItemId) {
        // ToDo: Validate if restaurantId on the endpoint and the restaurantId of the logged in RestaurantOwner is same
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            List<MenuItem> existingMenuItems = restaurant.get().getMenus();

            if(existingMenuItems == null) {
                existingMenuItems = new ArrayList<>();
                existingMenuItems.add(item);
            }
            else {
                existingMenuItems = replaceExistingItem(existingMenuItems, menuItemId,item);
            }
            restaurant.get().setMenus(existingMenuItems);
            restaurantRepository.save(restaurant.get());
            return restaurant.get();
        } else {
            return new Restaurant();
        }
    }

    private List<MenuItem> replaceExistingItem(List<MenuItem> existingMenuItems, Integer menuItemId, MenuItem item) {
        for(MenuItem existingItem : existingMenuItems) {
            if(existingItem.getMenuItemId() == menuItemId) {
                existingMenuItems.remove(existingItem);
                existingMenuItems.add(item);
                break;
            }
        }
        return existingMenuItems;
    }

    public Restaurant deleteMenuItemOfRestaurant(Integer restaurantId,
                                                 Integer menuItemId) {
        // ToDo: Validate if restaurantId on the endpoint and the restaurantId of the logged in RestaurantOwner is same
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isPresent()) {
            List<MenuItem> existingMenuItems = restaurant.get().getMenus();

            if(existingMenuItems == null || existingMenuItems.isEmpty()) {
                return restaurant.get();
            }
            else {
                for(MenuItem existingItem : existingMenuItems) {
                    if(existingItem.getMenuItemId() == menuItemId) {
                        existingMenuItems.remove(existingItem);
                        break;
                    }
                }
            }
            restaurant.get().setMenus(existingMenuItems);
            restaurantRepository.save(restaurant.get());
            return restaurant.get();
        } else {
            return new Restaurant();
        }
    }
}
