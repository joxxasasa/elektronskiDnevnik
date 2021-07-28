package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;

public interface StudentRepository extends CrudRepository<StudentEntity, Integer>{

	void save(UserEntity user);
	public StudentEntity findByUsername(String username);
	public StudentEntity findByParentId(Integer id);
	public List<StudentEntity> findByClassroomId(Integer classroomId);

}
