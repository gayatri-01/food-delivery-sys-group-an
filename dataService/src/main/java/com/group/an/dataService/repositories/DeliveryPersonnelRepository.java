package com.group.an.dataService.repositories;

import com.group.an.dataService.models.DeliveryPersonnel;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DeliveryPersonnelRepository extends MongoRepository <DeliveryPersonnel, Integer> {

    DeliveryPersonnel findByPersonnelId(int personnelId);
}
