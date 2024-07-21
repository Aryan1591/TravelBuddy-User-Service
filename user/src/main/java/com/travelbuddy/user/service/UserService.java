package com.travelbuddy.user.service;

import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.AboutMeDTO;
import com.travelbuddy.user.model.LoginDTO;
import com.travelbuddy.user.model.SignupDTO;
import com.travelbuddy.user.model.UpdateDTO;

import jakarta.validation.Valid;

public interface UserService {
    public String login(LoginDTO userSignup)throws UserNotFoundException, PasswordMismatchException;
    // public UserInfo register(UserInfo userInfo)throws DuplicateAccountException;
    public String register(SignupDTO userSignup)throws DuplicateAccountException;
    //UserInfo register(UserInfo userInfo) throws DuplicateAccountException;
    //String login(LoginDTO LoginDTO) throws UserNotFoundException, PasswordMismatchException;
    public AboutMeDTO getUserByUsername(String UserName);

    public String updateUserByUsername(UpdateDTO updateDTO, String UserName);

}
