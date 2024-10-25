package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrdersRepository extends MongoRepository <Orders,Integer> {
}
