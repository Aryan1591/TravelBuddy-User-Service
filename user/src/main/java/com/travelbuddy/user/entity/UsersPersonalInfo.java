package com.travelbuddy.user.entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usersPersonalInfo")
public class UsersPersonalInfo {

    @Id
    @NotEmpty(message = "Enter your name.")
    private String username;
    private String gender;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past(message = "Enter valid Date.")
    private Date dob;

    private boolean phnum_visibility;

}
