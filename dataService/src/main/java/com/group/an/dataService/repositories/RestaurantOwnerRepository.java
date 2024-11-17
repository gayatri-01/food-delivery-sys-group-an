package com.group.an.dataService.repositories;

import com.group.an.dataService.models.RestaurantOwner;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface RestaurantOwnerRepository extends MongoRepository<RestaurantOwner, Integer> {

    RestaurantOwner findByOwnerId(int restaurantOwnerId);

    List<RestaurantOwner> findByIsActive(boolean isActive);

}
