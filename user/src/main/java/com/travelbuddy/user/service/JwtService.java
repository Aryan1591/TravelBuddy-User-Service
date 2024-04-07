package com.travelbuddy.user.service;

import com.travelbuddy.user.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private JwtUtils jwtUtils;
    @Autowired
    public JwtService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public String generateJWTToken(String userName) {
        return jwtUtils.generateJWTToken(userName);
    }

    public void validateJWTToken(String jwtToken) {
        jwtUtils.validateJWTToken(jwtToken);
    }
}
