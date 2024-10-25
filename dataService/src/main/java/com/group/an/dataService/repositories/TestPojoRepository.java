package com.group.an.dataService.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.group.an.dataService.models.TestPojo;

public interface TestPojoRepository extends MongoRepository<TestPojo,String>{

}
