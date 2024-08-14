package com.travelbuddy.user.service;

import java.util.List;

public interface IUserPostsService {

    List<String> getPostIdsfromUsername(String userName);
    void addPostIdToUserBucket(String username, String postId);
    void removePostIdFromUserBucket(String username, String postId);
}
