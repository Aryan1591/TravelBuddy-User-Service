package com.travelbuddy.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AboutMeDTO {


    private String name;
    private String gender;

    private String phnumber;

    private Date dob;
    private String email;

    private boolean phnum_visibility;
    // private String password;
}
