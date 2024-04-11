package com.travelbuddy.user.entity;

import java.time.LocalDate;

import com.travelbuddy.user.constants.Constants.Gender;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

public class UserInfo {

	@Id
	@Column(name = "userName", nullable = false)
	private String userName;
	
	//local date
	@Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;
	
	//consider enum
	@Column(name = "gender", nullable = false)
	private Gender gender;
	
	@Column(name = "phoneNumber", nullable = false)
    private String phone;
	
	@Column(name = "location", nullable = false)
	private String location;
	
	//to store image as binary data we are using BLOB
	@Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] profilePicture;

}
