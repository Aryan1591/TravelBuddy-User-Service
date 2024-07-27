package com.travelbuddy.user.entity;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@Document
public class UsersPersonalInfo
{

 @Id
 @NotEmpty(message="Enter your name.")
 private String username;
 private String gender;
// private String phnumber;
 @DateTimeFormat(pattern="dd/MM/yyyy")
 @Past(message="Enter valid Date.")
 private Date dob;
 
 @Nonnull
 private boolean phnum_visibility;
 
// @Indexed(unique=true)
 //private String email;
 //private String password;
}
