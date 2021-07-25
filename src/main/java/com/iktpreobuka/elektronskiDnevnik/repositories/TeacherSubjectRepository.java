package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;

public interface TeacherSubjectRepository extends CrudRepository<TeacherSubject, Integer>{
	
	public List<TeacherEntity> findAllBySubjectId(Integer subjectId);

}
