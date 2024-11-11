package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository <Restaurant, Integer> {

    Restaurant findByRestaurantId(int restaurantId);
}
