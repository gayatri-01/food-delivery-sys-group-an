package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Admins;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminsRepository extends MongoRepository <Admins, Integer> {
}
