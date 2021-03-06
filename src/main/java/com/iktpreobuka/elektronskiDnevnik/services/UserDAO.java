package com.iktpreobuka.elektronskiDnevnik.services;

import javax.servlet.http.HttpServletRequest;

import com.iktpreobuka.elektronskiDnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;

public interface UserDAO {

	public UserEntity createNewUser(UserEntityDTO newUserDTO, Integer roleId);
	public UserEntity changeUser(Integer userId, Integer roleId, UserEntityDTO changedUser);
	public String getUsername(HttpServletRequest request);
}
