package com.travelbuddy.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserCredentials {

	@Id
	@Column(name = "userName", nullable = false)
	private String userName;
	
	@Column(name = "passWord", nullable = false)
    private String passWord;
	
	@Column(name = "email", nullable = false)
	private String email;

}
