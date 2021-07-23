package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;

public interface ParentRepository extends CrudRepository<ParentEntity, Integer>{

	void save(UserEntity user);
	
	public ParentEntity findByUsername(String username);
	public ParentEntity findByLastname(String lastname);

}
