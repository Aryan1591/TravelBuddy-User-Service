package com.travelbuddy.user.controller;

import com.travelbuddy.user.entity.UserCredentials;
import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.LoginDTO;
import com.travelbuddy.user.service.UserService;
import com.travelbuddy.user.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        log.info("Request received for Login");
        try {
            return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (PasswordMismatchException passwordMismatchException) {
            return new ResponseEntity<>(passwordMismatchException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserCredentials userInfo) {
        log.info("Request received for SignUp");
        try {
            return new ResponseEntity<>(userService.register(userInfo), HttpStatus.CREATED);
        } catch (DuplicateAccountException duplicateAccountException) {
            return new ResponseEntity<>(duplicateAccountException.getMessage(), HttpStatus.CONFLICT);
        }
    }
    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody UserCredentials userInfo) {
        log.info("Request received for Update User with id: {}", userName);
        try {
            return new ResponseEntity<>(userService.updateUser(userName, userInfo), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName) {
        log.info("Request received for Delete User with id: {}", userName);
        try {
            userService.deleteUser(userName);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
