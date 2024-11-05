package com.group.an.dataService.repositories;

import com.group.an.dataService.models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminRepository extends MongoRepository <Admin, Integer> {

    Admin findByAdminId(int adminId);

}
