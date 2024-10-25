package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Restaurants;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository <Restaurants, Integer> {
}
