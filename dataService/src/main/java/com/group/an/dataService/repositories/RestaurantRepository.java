package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepository extends MongoRepository <Restaurant, Integer> {

    Restaurant findByRestaurantId(int restaurantId);
    List<Restaurant> findTop5ByOrderByUserRatingsDesc();
}
