package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;

public interface ParentDAO {
	
	public ParentEntity createParent(UserEntity newUser, Integer roleId);
	public ParentEntity changeParent(UserEntity user, UserEntityDTO changedUser, Integer roleId);

}
