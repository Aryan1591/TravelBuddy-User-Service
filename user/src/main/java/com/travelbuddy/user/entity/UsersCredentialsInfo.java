package com.travelbuddy.user.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class UsersCredentialsInfo {

    @Id
    @NotBlank(message = "Please provide a username")
    private String username;

    @Indexed(unique = true)
    @Email(message = "Please provide a valid email address", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @Size(min = 8, message = "Password must be atleast 8 and max of 16 characters long")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8}", message = "Password should contains At least one lowercase letter,one uppercase letter,one numeric value and one special symbol ")
    private String password;
    @Size(min = 12, message = "Message must be minimum of 12 digits including country code")
    @Digits(message = "Number should contain max of 15 digits.", fraction = 0, integer = 15)
    private String phnumber;
}
