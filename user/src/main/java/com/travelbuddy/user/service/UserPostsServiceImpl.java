package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UserPost;
import com.travelbuddy.user.repository.UserPostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserPostsServiceImpl implements IUserPostsService {

  @Autowired
  UserPostsRepository userPostsRepository;

  /**
   * @param userName the userName of the user
   * @return returns the UserPosts Entity of the User
   */
  @Override
  public List<String> getPostIdsfromUsername(String userName) {
    return userPostsRepository
        .findById(userName)
        .map(UserPost::getPostIds)
        .orElse(new ArrayList<>());
  }


  @Override
  public void addPostIdToUserBucket(String username, String postId) {
    UserPost userPosts = userPostsRepository.findById(username).orElse(null);
    if (Objects.isNull(userPosts)) {
      userPosts = new UserPost();
      userPosts.setUsername(username);
      userPosts.setPostIds(new ArrayList<>());
    }
    List<String> existingIds = userPosts.getPostIds();
    existingIds.add(postId);
    userPosts.setPostIds(existingIds);
    userPostsRepository.save(userPosts);
  }

}
