package com.travelbuddy.user.controller;

import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.LoginDTO;
import com.travelbuddy.user.model.PasswordDTO;
import com.travelbuddy.user.model.SignupDTO;
import com.travelbuddy.user.model.UpdateDTO;
import com.travelbuddy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/users")
@CrossOrigin(origins = {"https://66bd09f19028ae026b5372d9--courageous-bublanina-dd5958.netlify.app","https://travelbuddy-posts-service-production.up.railway.app"})
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        log.info("Request received for Login");
        try {
            return new ResponseEntity<>(userService.login(loginDTO), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PasswordMismatchException passwordMismatchException) {
            return new ResponseEntity<>(passwordMismatchException.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid SignupDTO signupDTO) {
        log.info("Request received for SignUp");
        try {
            return new ResponseEntity<>(userService.register(signupDTO), HttpStatus.CREATED);
        } catch (DuplicateAccountException duplicateAccountException) {
            return new ResponseEntity<>(duplicateAccountException.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/getInfo/{userName}")
    public ResponseEntity<?> aboutMe(@PathVariable String userName) {
        log.info("Request received for AboutMe " + userName);
        // return userService.getUserByUsername(userName);
        try {
            return new ResponseEntity<>(userService.getUserByUsername(userName), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/updateInfo/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateDTO updateDTO, @PathVariable String userName) {
        log.info("Request received to UpdateUser ");
        try {
            return new ResponseEntity<>(userService.updateUserByUsername(updateDTO, userName), HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAccount/{userName}")
    public ResponseEntity<?> deleteUser(@PathVariable String userName) {
        log.info("Request received for DeleteUser " + userName);
        // return userService.getUserByUsername(userName);
        try {
            userService.deleteUserByUsername(userName);
            return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/changePassword/{userName}")
    public ResponseEntity<?> changePassword(@RequestBody @Valid PasswordDTO passwordDTO, @PathVariable String userName) {
        log.info("Request received for Changing Password " + userName);
        // return userService.getUserByUsername(userName);
        try {
            userService.changePassword(passwordDTO, userName);
            return new ResponseEntity<>("Password has been updated Successfully", HttpStatus.OK);
        } catch (UserNotFoundException userNotFoundException) {
            return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (PasswordMismatchException passwordMismatchException) {
            return new ResponseEntity<>(passwordMismatchException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/gender/{username}")
    public String getGenderFromUsername(@PathVariable String username) {
        try {
            return userService.fetchGender(username);
        } catch (UserNotFoundException e) {
            return "This user has not been registered";
        }
    }


    @GetMapping("/email/{username}")
    public String getEmailFromUsername(@PathVariable String username) {
        try {
            return userService.fetchEmail(username);
        } catch (UserNotFoundException e) {
            return "This user has not been registered";
        }
    }
}
