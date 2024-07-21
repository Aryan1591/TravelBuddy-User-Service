package com.travelbuddy.user.model;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupDTO {


    @Id
    @NotEmpty(message = "Enter your name.")
    private String username;
    private String gender;
    @Size(min = 12, message = "Message must be minimum of 12 digits including country code")
    @Digits(message = "Number should contain max of 15 digits.", fraction = 0, integer = 15)
    private String phnumber;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Enter valid Date.")
    private Date dob;
    @Email(message = "Please provide a valid email address", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @Size(min = 8, message = "Password must be atleast 8 and max of 16 characters long")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,16}", message = "Password should contains At least one lowercase letter,one uppercase letter,one numeric value and one special symbol ")
    private String password;

    @Nonnull
    private boolean phnum_visibility = true;

}
