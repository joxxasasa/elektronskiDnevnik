package com.iktpreobuka.elektronskiDnevnik.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;

public interface TeacherSubjectClassroomRepository extends CrudRepository<TeacherSubjectClassroom, Integer>{
	
	public List<TeacherSubjectClassroom> findByClassroom(ClassroomEntity classroom);
//	public TeacherSubjectClassroom findByTeacherSubjectId(Integer teacherSubjectId);
	public TeacherSubjectClassroom findByTeacherSubjectIdAndClassroomId(Integer teacherSubjectId, Integer classroomId);

}
