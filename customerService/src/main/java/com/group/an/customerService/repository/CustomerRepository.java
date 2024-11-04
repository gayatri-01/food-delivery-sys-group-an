package com.group.an.customerService.repository;

//import com.group.an.customerService.entity.Customer;
//import org.springframework.data.jpa.repository.JpaRepository;
import com.group.an.dataService.models.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, Long> {
}
