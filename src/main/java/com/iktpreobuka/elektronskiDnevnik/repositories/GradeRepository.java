package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;

public interface GradeRepository extends CrudRepository<GradeEntity, Integer>{
	
//	public List<GradeEntity> findAllByTeacherSubjectClassroomIdByTeacher_SubjectIdByTeacherId(Integer id);
	public List<GradeEntity> findByStudentId(Integer studentId);
	public List<GradeEntity> findByTeacherSubjectClassroomId(Integer teacherSubjectClassroomId);

}
