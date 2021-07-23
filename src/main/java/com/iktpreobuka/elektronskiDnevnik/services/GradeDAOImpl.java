package com.iktpreobuka.elektronskiDnevnik.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.GradeEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;

@Service
public class GradeDAOImpl {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroom;
	
	@Autowired
	private GradeRepository gradeRepository;
	
	public GradeEntity gradingStudent(Integer studentId, Integer teacherSubjectClassroomId, GradeEntityDTO newGradeDTO) {
		GradeEntity grade = new GradeEntity();
		grade.setDate(LocalDate.now());
		grade.setSemester(newGradeDTO.getSemester());
		grade.setValue(newGradeDTO.getValue());
		grade.setStudent(studentRepository.findById(studentId).get());
		grade.setTeacherSubjectClassroom(teacherSubjectClassroom.findById(teacherSubjectClassroomId).get());
		gradeRepository.save(grade);
		return grade;
	}

}
