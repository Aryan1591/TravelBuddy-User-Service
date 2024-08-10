package com.travelbuddy.user.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document(collection = "userPosts")
public class UserPost {

    @MongoId
    private String username;
    private List<String> postIds;

}
