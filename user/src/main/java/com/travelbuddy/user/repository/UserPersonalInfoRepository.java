package com.travelbuddy.user.repository;

import com.travelbuddy.user.entity.UsersPersonalInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPersonalInfoRepository extends MongoRepository<UsersPersonalInfo, String> {


}
