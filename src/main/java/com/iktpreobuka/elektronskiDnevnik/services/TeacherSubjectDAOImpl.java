package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectRepository;

@Service
public class TeacherSubjectDAOImpl {
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	public TeacherSubject assignSubjectTeacher(Integer teacherId, Integer subjectId) {
		TeacherSubject teacherSubject = new TeacherSubject();
		teacherSubject.setTeacher(teacherRepository.findById(teacherId).get());
		teacherSubject.setSubject(subjectRepository.findById(subjectId).get());	
		teacherSubjectRepository.save(teacherSubject);
		return teacherSubject;
	}

}
