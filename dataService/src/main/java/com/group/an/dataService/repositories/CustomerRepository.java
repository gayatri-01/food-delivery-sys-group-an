package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository <Customer,Integer> {

    Customer findByCustomerId(int customerId);

}
