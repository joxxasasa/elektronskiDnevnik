package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;

public interface TeacherRepository extends CrudRepository<TeacherEntity, Integer>{

	void save(UserEntity user);
	public TeacherEntity findByUsername(String username);

}
