package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>{

	void save(UserEntity user);
	
	public StudentEntity findByUsername(String username);

}
