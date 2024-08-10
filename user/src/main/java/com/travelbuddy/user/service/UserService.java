package com.travelbuddy.user.service;

import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.*;

public interface UserService {
    public String login(LoginDTO userSignup) throws UserNotFoundException, PasswordMismatchException;

    public String register(SignupDTO userSignup) throws DuplicateAccountException;

    public AboutMeDTO getUserByUsername(String UserName) throws UserNotFoundException;

    public String updateUserByUsername(UpdateDTO updateDTO, String UserName) throws UserNotFoundException;

    public void deleteUserByUsername(String UserName) throws UserNotFoundException;

    public void changePassword(PasswordDTO passwordDTO, String UserName) throws UserNotFoundException, PasswordMismatchException;

    public String fetchGender(String userName) throws UserNotFoundException;

    public void addPostIdToUserBucket(String username, String postId);

    public String fetchEmail(String userName) throws UserNotFoundException;
}
