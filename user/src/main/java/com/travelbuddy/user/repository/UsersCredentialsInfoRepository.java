package com.travelbuddy.user.repository;

import com.travelbuddy.user.entity.UsersCredentialsInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersCredentialsInfoRepository extends MongoRepository<UsersCredentialsInfo, String> {

}
