package com.travelbuddy.user.repository;

import com.travelbuddy.user.entity.UserPost;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPostsRepository extends MongoRepository<UserPost,String> {

}
