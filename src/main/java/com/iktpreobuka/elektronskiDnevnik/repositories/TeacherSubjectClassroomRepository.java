package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;

public interface TeacherSubjectClassroomRepository extends CrudRepository<TeacherSubjectClassroom, Integer>{
	
	public List<TeacherSubjectClassroom> findByClassroomId(Integer classroomId);
	public TeacherSubjectClassroom findByTeacherSubjectIdAndClassroomId(Integer teacherSubjectId, Integer classroomId);
	public List<TeacherSubjectClassroom> findByTeacherSubjectId(Integer teacherSubjectId);

}
