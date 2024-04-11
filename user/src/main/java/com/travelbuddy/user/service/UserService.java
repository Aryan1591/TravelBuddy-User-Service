package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UserCredentials;
import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.LoginDTO;

public interface UserService {
    public String login(LoginDTO loginDTO)throws UserNotFoundException, PasswordMismatchException;
    public UserCredentials register(UserCredentials userInfo)throws DuplicateAccountException;
    public UserCredentials updateUser(String userName, UserCredentials userInfo) throws UserNotFoundException;
    public void deleteUser(String userName) throws UserNotFoundException;
}
