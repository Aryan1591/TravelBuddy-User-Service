package com.travelbuddy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.AboutMeDTO;
import com.travelbuddy.user.model.LoginDTO;
import com.travelbuddy.user.model.SignupDTO;
import com.travelbuddy.user.model.UpdateDTO;
import com.travelbuddy.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:5173"})
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
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
    public ResponseEntity<?> createUser(@RequestBody @Valid SignupDTO signupDTO) {
        log.info("Request received for SignUp");
        try {
            return new ResponseEntity<>(userService.register(signupDTO), HttpStatus.CREATED);
        } catch (DuplicateAccountException duplicateAccountException) {
            return new ResponseEntity<>(duplicateAccountException.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/getInfo/{userName}")
    public ResponseEntity<AboutMeDTO> AboutMe(@PathVariable String userName)
    {
        log.info("Request received for AboutMe "+userName);
        // return userService.getUserByUsername(userName);
        return new ResponseEntity<>(userService.getUserByUsername(userName),HttpStatus.OK);

    }

    @PutMapping("/updateInfo/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateDTO updateDTO,@PathVariable String userName)
    {
        log.info("Request received to UpdateUser ");
        return new ResponseEntity<>(userService.updateUserByUsername(updateDTO,userName),HttpStatus.OK);

    }
}
