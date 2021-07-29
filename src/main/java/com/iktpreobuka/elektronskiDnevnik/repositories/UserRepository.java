package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{
	
	public UserEntity findByUsername(String username);
	public List<UserEntity> findByRoleId(Integer roleId);

}
