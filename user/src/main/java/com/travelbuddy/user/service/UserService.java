package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UserInfo;
import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.LoginDTO;

public interface UserService {
    public String login(LoginDTO loginDTO)throws UserNotFoundException, PasswordMismatchException;
    public UserInfo register(UserInfo userInfo)throws DuplicateAccountException;
}
