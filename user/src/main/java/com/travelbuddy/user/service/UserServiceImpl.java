package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UsersCredentialsInfo;
import com.travelbuddy.user.entity.UsersPersonalInfo;
import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.*;
import com.travelbuddy.user.repository.UserPersonalInfoRepository;
import com.travelbuddy.user.repository.UsersCredentialsInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserPersonalInfoRepository userpersonalinfoRepository;
    @Autowired
    UsersCredentialsInfoRepository userscredentialsinfoRepository;
    @Autowired
    private JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String login(@NotNull LoginDTO loginDTO) throws UserNotFoundException, PasswordMismatchException {
        Optional<UsersCredentialsInfo> userDetails = userscredentialsinfoRepository.findById(loginDTO.getUsername());
        if (userDetails.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), userDetails.get().getPassword()))
            throw new PasswordMismatchException("Password is invalid");

        /*
         * TODO Check for login Details
         *
         * Authentication authentication = authenticationManager.authenticate(new
         * UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
         * loginDTO.getPassWord())); if (Objects.nonNull(authentication) &&
         * authentication.isAuthenticated()) { return
         * jwtService.generateJWTToken(loginDTO.getUsername()); }
         */
        return jwtService.generateJWTToken(loginDTO.getUsername());
    }

    /*
     * @Override public UserInfo register(UserInfo userInfo) throws
     * DuplicateAccountException { Optional<UserInfo> userDetails =
     * userRepository.findById(userInfo.getUsername()); if (!userDetails.isEmpty())
     * { throw new
     * DuplicateAccountException("Username Already Exists, choose different Username"
     * ); } userRepository.save(userInfo); return userInfo; }
     */

    @Override
    public String register(@NotNull SignupDTO signupDTO) throws DuplicateAccountException {
        UsersPersonalInfo userspersonalinfo = new UsersPersonalInfo(signupDTO.getUsername(), signupDTO.getGender(), signupDTO.getDob(), true);

        UsersCredentialsInfo userscredentialsinfo = new UsersCredentialsInfo(signupDTO.getUsername(), signupDTO.getEmail()
                , this.passwordEncoder.encode(signupDTO.getPassword()), signupDTO.getPhnumber());

        Optional<UsersPersonalInfo> userDetails = userpersonalinfoRepository.findById(signupDTO.getUsername());
        if (!userDetails.isEmpty()) {
            throw new DuplicateAccountException("Username Already Exists, choose different Username");
        }
        /*
         * if(usersignup.getName()) { throw new
         * DuplicateAccountException("Username Already Exists, choose different Username"
         * ); }
         */
        userpersonalinfoRepository.save(userspersonalinfo);
        userscredentialsinfoRepository.save(userscredentialsinfo);
        return signupDTO.getUsername();
    }


    @Override
    public AboutMeDTO getUserByUsername(String UserName) {

        Optional<UsersPersonalInfo> personalInfoOpt = userpersonalinfoRepository.findById(UserName);
        Optional<UsersCredentialsInfo> credentialsInfoOpt = userscredentialsinfoRepository.findById(UserName);
        if (personalInfoOpt.isEmpty())
            throw new UserNotFoundException("User Not Found");

        return mapToDTO(personalInfoOpt.get(), credentialsInfoOpt.get());
    }

    @NotNull
    private AboutMeDTO mapToDTO(@NotNull final UsersPersonalInfo personalinfo, @NotNull final UsersCredentialsInfo credentialsinfo) {
        AboutMeDTO aboutMeDTO = new AboutMeDTO();
        aboutMeDTO.setName(personalinfo.getUsername());
        aboutMeDTO.setEmail(credentialsinfo.getEmail());
        aboutMeDTO.setDob(personalinfo.getDob());
        aboutMeDTO.setGender(personalinfo.getGender());
        aboutMeDTO.setPhnum_visibility(personalinfo.isPhnum_visibility());
        aboutMeDTO.setPhnumber(credentialsinfo.getPhnumber());
        return aboutMeDTO;
    }


    public String updateUserByUsername(UpdateDTO updateDTO, String userName) {
        // Retrieve user personal information
        Optional<UsersPersonalInfo> updatePersonalInfo = userpersonalinfoRepository.findById(userName);
        // Retrieve user credentials information
        Optional<UsersCredentialsInfo> updateCredentialsInfo = userscredentialsinfoRepository.findById(userName);

        if (updatePersonalInfo.isPresent()) {
            UsersPersonalInfo updateUsersPersonalInfo = updatePersonalInfo.get();
            UsersCredentialsInfo updateUsersCredentialsInfo = updateCredentialsInfo.get();
            updateUsersPersonalInfo.setGender(updateDTO.getGender());
            updateUsersPersonalInfo.setDob(updateDTO.getDob());

            log.info("" + updateDTO.isPhnum_visibility());
            updateUsersPersonalInfo.setPhnum_visibility(updateDTO.isPhnum_visibility());

            updateUsersCredentialsInfo.setPhnumber(updateDTO.getPhnumber());
            updateUsersCredentialsInfo.setEmail(updateDTO.getEmail());

            userpersonalinfoRepository.save(updateUsersPersonalInfo);
            userscredentialsinfoRepository.save(updateUsersCredentialsInfo);
        } else {
            throw new UserNotFoundException("User not found");
        }
        return userName;

    }

    public void deleteUserByUsername(String userName) {
        Optional<UsersPersonalInfo> user = userpersonalinfoRepository.findById(userName);
        if (user.isEmpty())
            throw new UserNotFoundException("User Not Found");
        userpersonalinfoRepository.deleteById(userName);
        userscredentialsinfoRepository.deleteById(userName);
    }

    public void changePassword(PasswordDTO passwordDTO, String userName) {
        Optional<UsersCredentialsInfo> updateCredentialsInfo = userscredentialsinfoRepository.findById(userName);
        if (updateCredentialsInfo.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        UsersCredentialsInfo updateUsersCredentialsInfo = updateCredentialsInfo.get();
        String currentPassword = passwordDTO.getCurrentPassword();
        String newPassword = passwordDTO.getConfirmPassword();

        if (!passwordEncoder.matches(currentPassword, updateUsersCredentialsInfo.getPassword())) {
            throw new PasswordMismatchException("Password is invalid");
        }

// Add a null check and logging for the new password
        if (newPassword == null || newPassword.isEmpty()) {
            log.error("New password is null or empty");
            throw new IllegalArgumentException("New password cannot be null or empty");
        }
        updateUsersCredentialsInfo.setPassword(passwordEncoder.encode(newPassword));
        userscredentialsinfoRepository.save(updateUsersCredentialsInfo);
    }
}
