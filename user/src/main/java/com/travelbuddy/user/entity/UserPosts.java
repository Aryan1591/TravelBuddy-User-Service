package com.travelbuddy.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "travelbuddy.userposts")
public class UserPosts {
    @MongoId
    private String username;
    private List<String> postIds;
}
