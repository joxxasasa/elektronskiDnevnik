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
public class GradeDAOImpl implements GradeDAO{
	
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
	
	public GradeEntity deleteGrade(Integer gradeId) {
		GradeEntity grade = gradeRepository.findById(gradeId).get();
		gradeRepository.delete(grade);
		return grade;
	}
	
	public GradeEntity updateGrade(Integer gradeId, GradeEntityDTO changedGradeDTO) {
		GradeEntity grade = gradeRepository.findById(gradeId).get();
		grade.setDate(LocalDate.now());
		if(changedGradeDTO.getSemester() != null) {
			grade.setSemester(changedGradeDTO.getSemester());
		}
		if(changedGradeDTO.getValue() != null) {
			grade.setValue(changedGradeDTO.getValue());
		}
		grade.setStudent(grade.getStudent());
		grade.setTeacherSubjectClassroom(grade.getTeacherSubjectClassroom());
		gradeRepository.save(grade);
		return grade;
	}
	

}
