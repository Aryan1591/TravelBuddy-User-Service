package com.travelbuddy.user.controller;

import com.travelbuddy.user.service.IUserPostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/userPosts")
public class UserPostsController {

  @Autowired
  IUserPostsService userPostsService;

  @GetMapping("/getPostIdsOfUser/{userName}")
  ResponseEntity<List<String>> getPostIdsOfUsername(@PathVariable String userName) {
    return new ResponseEntity<>(userPostsService.getPostIdsfromUsername(userName), HttpStatus.OK);
  }

  @PostMapping("/{username}/posts/add")
  public String addPostIdToUserBucket(@PathVariable String username, @RequestParam String postId) {
    userPostsService.addPostIdToUserBucket(username, postId);
    return String.format("PostId %s has been successfully added to username %s list", postId, username);
  }
}
