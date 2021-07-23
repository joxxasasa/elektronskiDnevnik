package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectRepository;

@Service
public class TeacherSubjectClassroomDAOImpl {
	
	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	
	@Autowired
	private ClassroomRepository classroomRepository;
	
	public TeacherSubjectClassroom assignTeacherSubjectToClassroom(Integer teacherSubjectId, Integer classroomId) {
		TeacherSubjectClassroom teacherSubjectClassroom = new TeacherSubjectClassroom();
		teacherSubjectClassroom.setTeacherSubject(teacherSubjectRepository.findById(teacherSubjectId).get());
		teacherSubjectClassroom.setClassroom(classroomRepository.findById(classroomId).get());
		teacherSubjectClassroomRepository.save(teacherSubjectClassroom);
		return teacherSubjectClassroom;
	}

}
