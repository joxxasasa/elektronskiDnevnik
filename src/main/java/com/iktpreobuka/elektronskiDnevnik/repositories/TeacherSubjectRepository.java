package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;

public interface TeacherSubjectRepository extends CrudRepository<TeacherSubject, Integer>{
	
	public List<TeacherEntity> findAllBySubjectId(Integer subjectId);
	public TeacherSubject findByTeacherId(Integer teacherId);
	public TeacherSubject findBySubjectId(Integer subjectId);
	public List<TeacherSubject> findByTeacherSubjectClassroomsClassroomId(Integer classroomId);
	
	
}
