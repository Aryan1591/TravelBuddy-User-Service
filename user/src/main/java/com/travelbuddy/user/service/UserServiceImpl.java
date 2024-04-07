package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UserInfo;
import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.LoginDTO;
import com.travelbuddy.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDTO loginDTO) throws UserNotFoundException, PasswordMismatchException {
        Optional<UserInfo> userDetails = userRepository.findById(loginDTO.getUserName());
        if (userDetails.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(loginDTO.getPassWord(), userDetails.get().getPassWord()))
            throw new PasswordMismatchException("Password is invalid");

        /*
           TODO
           Check for login Details

           Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassWord()));
        if (Objects.nonNull(authentication) && authentication.isAuthenticated()) {
            return jwtService.generateJWTToken(loginDTO.getUserName());
        }
         */
        return jwtService.generateJWTToken(loginDTO.getUserName());
    }

    @Override
    public UserInfo register(UserInfo userInfo) throws DuplicateAccountException {
        Optional<UserInfo> userDetails = userRepository.findById(userInfo.getUserName());
        if (!userDetails.isEmpty()) {
            throw new DuplicateAccountException("UserName Already Exists, choose different username");
        }
        userRepository.save(userInfo);
        return userInfo;
    }
}
