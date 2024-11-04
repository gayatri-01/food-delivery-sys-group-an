package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Customers;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomersRepository extends MongoRepository <Customers,Integer> {
}
