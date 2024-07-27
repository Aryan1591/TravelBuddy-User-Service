package com.travelbuddy.user.model;

import org.springframework.data.annotation.Id;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @Id
    @NotEmpty(message="Enter your Username.")
    private String Username;
    @Size(min=8,message="Password must be atleast 8 and max of 16 characters long")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,16}", message = "Password should contains At least one lowercase letter,one uppercase letter,one numeric value and one special symbol ")
    private String password;
}