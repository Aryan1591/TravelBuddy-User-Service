package com.travelbuddy.user.model;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDTO {

    private String gender;
    @Size(min = 12, message = "Message must be minimum of 12 digits including country code")
    @Digits(message = "Number should contain max of 15 digits.", fraction = 0, integer = 15)
    private String phnumber;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Enter valid Date.")
    private Date dob;
    @Email(message = "Please provide a valid email address", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;
    @Nonnull
    private boolean phnum_visibility;
}