package com.group.an.dataService.repositories;

import com.group.an.dataService.models.RestaurantOwner;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RestaurantOwnerRepository extends MongoRepository<RestaurantOwner, Integer> {

    RestaurantOwner findByOwnerId(int restaurantOwnerId);

}
