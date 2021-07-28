package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;

public interface TeacherDAO {
	
	public List<TeacherEntity> findTeachersBySubject(Integer subject);

	List<TeacherEntity> findTeachersBySubjectAndClassroom(Integer subject, Integer classroom);

}
