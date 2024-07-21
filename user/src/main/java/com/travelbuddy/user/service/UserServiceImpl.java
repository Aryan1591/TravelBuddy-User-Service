package com.travelbuddy.user.service;

import com.travelbuddy.user.entity.UsersCredentialsInfo;
import com.travelbuddy.user.entity.UsersPersonalInfo;
import com.travelbuddy.user.exception.DuplicateAccountException;
import com.travelbuddy.user.exception.PasswordMismatchException;
import com.travelbuddy.user.exception.UserNotFoundException;
import com.travelbuddy.user.model.AboutMeDTO;
import com.travelbuddy.user.model.LoginDTO;
import com.travelbuddy.user.model.SignupDTO;
import com.travelbuddy.user.model.UpdateDTO;
import com.travelbuddy.user.repository.UserPersonalInfoRepository;
import com.travelbuddy.user.repository.UsersCredentialsInfoRepository;
import lombok.extern.slf4j.Slf4j;
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
    public String login(LoginDTO loginDTO) throws UserNotFoundException, PasswordMismatchException {
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
    public String register(SignupDTO signupDTO) throws DuplicateAccountException {
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

        UsersPersonalInfo personalInfoOpt = userpersonalinfoRepository.findById(UserName).get();
        UsersCredentialsInfo credentialsInfoOpt = userscredentialsinfoRepository.findById(UserName).get();
        return mapToDTO(personalInfoOpt, credentialsInfoOpt);
    }

    private AboutMeDTO mapToDTO(final UsersPersonalInfo personalinfo, final UsersCredentialsInfo credentialsinfo) {
        AboutMeDTO aboutMeDTO = new AboutMeDTO();
        aboutMeDTO.setName(personalinfo.getUsername());
        aboutMeDTO.setEmail(credentialsinfo.getEmail());
        aboutMeDTO.setDob(personalinfo.getDob());
        aboutMeDTO.setGender(personalinfo.getGender());
        aboutMeDTO.setPhnum_visibility(personalinfo.isPhnum_visibility());
        //	aboutMeDTO.setPassword(credentialsinfo.getPassword());
        aboutMeDTO.setPhnumber(credentialsinfo.getPhnumber());
        return aboutMeDTO;
    }


    public String updateUserByUsername(UpdateDTO updateDTO, String userName) {
        // Retrieve user personal information
        UsersPersonalInfo updatePersonalInfo = userpersonalinfoRepository.findById(userName).get();
        // Retrieve user credentials information
        UsersCredentialsInfo updateCredentialsInfo = userscredentialsinfoRepository.findById(userName).get();

        // Update personal information
//		updatePersonalInfo.setName(updateDTO.getName());
        updatePersonalInfo.setGender(updateDTO.getGender());
        updatePersonalInfo.setDob(updateDTO.getDob());
        //System.out.println(updateDTO.isPhnum_visibility());
        log.info("" + updateDTO.isPhnum_visibility());
        updatePersonalInfo.setPhnum_visibility(updateDTO.isPhnum_visibility());

        // Update credentials information
//		updateCredentialsInfo.setUsername(updateDTO.getName());
        updateCredentialsInfo.setPhnumber(updateDTO.getPhnumber());
        updateCredentialsInfo.setEmail(updateDTO.getEmail());

//		// Delete the previous records as the username itself is changing we won't be
//		// having uniqueness
//		userpersonalinfoRepository.deleteById(userName);
//		userscredentialsinfoRepository.deleteById(userName);

        // Save the updated entities
        userpersonalinfoRepository.save(updatePersonalInfo);
        userscredentialsinfoRepository.save(updateCredentialsInfo);

        return userName;

    }

}
