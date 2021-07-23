package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;

@Service
public class StudentDAOImpl implements StudentDAO{
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ClassroomRepository classroomRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	public StudentEntity assignToClassroom(Integer studentId, Integer classroomId) {
		
				StudentEntity student = studentRepository.findById(studentId).get();
				ClassroomEntity classroom = classroomRepository.findById(classroomId).get();
				student.setClassroom(classroom);
				studentRepository.save(student);
				return student;
			
	}
	
	public StudentEntity assignToParent(Integer studentId, Integer parentId) {
		StudentEntity student = studentRepository.findById(studentId).get();
		ParentEntity parent = parentRepository.findById(parentId).get();
		student.setParent(parent);
		studentRepository.save(student);
		return student;
	}

}
