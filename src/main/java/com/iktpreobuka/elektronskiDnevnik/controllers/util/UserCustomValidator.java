package com.iktpreobuka.elektronskiDnevnik.controllers.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;

@Component
public class UserCustomValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		UserEntityDTO.class.equals(clazz);
		return false;
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		UserEntityDTO newUser = (UserEntityDTO) target;
		
		if(newUser.getPassword() != null && !newUser.getPassword().equals(newUser.getConfirmPassword())) {
			errors.reject("400", "Passwords must match!");
		}
		
	}

}
