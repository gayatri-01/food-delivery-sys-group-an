package com.group.an.dataService.repositories;

import com.group.an.dataService.models.DeliveryPersonnels;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryPersonnelRepository extends MongoRepository <DeliveryPersonnels, Integer> {
}
