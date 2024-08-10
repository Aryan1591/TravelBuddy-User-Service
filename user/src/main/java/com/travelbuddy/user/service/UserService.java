package com.travelbuddy.user.service;

import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.LoginDTO;
import com.travelbuddy.user.model.SignupDTO;
import com.travelbuddy.user.model.PasswordDTO;
import com.travelbuddy.user.model.AboutMeDTO;
import com.travelbuddy.user.model.UpdateDTO;

public interface UserService {
    String login(LoginDTO userSignup) throws UserNotFoundException, PasswordMismatchException;

    String register(SignupDTO userSignup) throws DuplicateAccountException;

    AboutMeDTO getUserByUsername(String UserName) throws UserNotFoundException;

    String updateUserByUsername(UpdateDTO updateDTO, String UserName) throws UserNotFoundException;

    void deleteUserByUsername(String UserName) throws UserNotFoundException;

    void changePassword(PasswordDTO passwordDTO, String UserName) throws UserNotFoundException, PasswordMismatchException;

    String fetchGender(String userName) throws UserNotFoundException;

    String fetchEmail(String userName) throws UserNotFoundException;
}
