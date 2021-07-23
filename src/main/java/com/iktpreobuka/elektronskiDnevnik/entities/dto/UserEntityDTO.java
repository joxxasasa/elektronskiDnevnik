package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.iktpreobuka.elektronskiDnevnik.entities.RoleEntity;

public class UserEntityDTO {
	
	@NotBlank(message = "Username must be provided")
	@Column(nullable = false, unique = true)
	@Size(min = 6, max = 20, message = "Username must be string between {min} and {max} chars!")
	private String username;
	
	@NotBlank(message = "Password must not be empty!")
	@Size(min = 5, max = 10, message = "Password must be string between {min} and {max} chars!")
	private String password;
	
	@NotBlank(message = "ConfirmPassword must not be empty!")
	@Size(min = 5, max = 10, message = "ConfirmPassword must be string between {min} and {max} chars!")
	private String confirmPassword;
	
	@NotBlank(message = "Name must not be empty!")
	@Size(min = 2, max = 20, message = "Name must be string between {min} and {max} chars!")
	private String name;
	
	@NotBlank(message = "Lastname must not be empty!")
	@Size(min = 5, max = 10, message = "Lastname must be string between {min} and {max} chars!")
	private String lastname;
	
	@NotBlank(message = "Email must be provided!")
	@Email(message = "Email must be valid!")
	private String email;
	
	private boolean isActive;
	
	private RoleEntity role;

	public UserEntityDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

}
