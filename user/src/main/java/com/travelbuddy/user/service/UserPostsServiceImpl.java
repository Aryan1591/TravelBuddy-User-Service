package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UserPost;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.repository.UserPostsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
  @Override
  public void removePostIdFromUserBucket(String username, String postId) {
    UserPost userPosts = userPostsRepository.findById(username).orElse(null);
    //userPosts can't be null
    if (Objects.isNull(userPosts)) {
      throw new UserNotFoundException(String.format("Account is not registered with this user %s",username));
    }
    userPosts.setPostIds(userPosts.getPostIds().stream().filter(ids -> !ids.equals(postId)).collect(Collectors.toList()));
    userPostsRepository.save(userPosts);
  }

}
