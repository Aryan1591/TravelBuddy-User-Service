package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UserCredentials;
import com.travelbuddy.user.model.CustomizedUserDetails;
import com.travelbuddy.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserLoaderService implements UserDetailsService {
   @Autowired
   private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> userDetail = userRepository.findById(username);
        return userDetail.map(CustomizedUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with %s",username)));
    }
}
