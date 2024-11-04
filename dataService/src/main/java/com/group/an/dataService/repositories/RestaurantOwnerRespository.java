package com.group.an.dataService.repositories;

import com.group.an.dataService.models.RestaurantOwners;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantOwnerRespository extends MongoRepository<RestaurantOwners, Integer> {
}
