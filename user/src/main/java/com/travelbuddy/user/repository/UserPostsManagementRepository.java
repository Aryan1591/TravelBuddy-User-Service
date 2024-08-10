package com.travelbuddy.user.repository;

import com.travelbuddy.user.entity.UserPosts;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPostsManagementRepository extends MongoRepository<UserPosts, String> {

}
