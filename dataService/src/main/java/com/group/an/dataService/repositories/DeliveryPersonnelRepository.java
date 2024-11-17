package com.group.an.dataService.repositories;

import com.group.an.dataService.models.DeliveryPersonnel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface DeliveryPersonnelRepository extends MongoRepository <DeliveryPersonnel, Integer> {

    DeliveryPersonnel findByPersonnelId(int personnelId);

    List<DeliveryPersonnel> findByIsActive(boolean isActive);
}
