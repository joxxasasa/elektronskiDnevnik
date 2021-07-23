package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.services.StudentDAOImpl;

@RestController
@RequestMapping(value = "/students")
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ClassroomRepository classroomRepository;
	
	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private StudentDAOImpl studentDAOImpl;

	@PatchMapping("/assignToClassroom/studentId/{studentId}/classroomId/{classroomId}")
	public ResponseEntity<?> assignStudentToClassroom(@PathVariable Integer studentId,
			@PathVariable Integer classroomId) {

		try {
			if (studentRepository.existsById(studentId)) {
				if (classroomRepository.existsById(classroomId)) {
					StudentEntity student = studentDAOImpl.assignToClassroom(studentId, classroomId);
					return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
				}
				return new ResponseEntity<RESTError>(new RESTError(1, "Classroom not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PatchMapping("/assignStudentToParent/studentId/{studentId}/parentId/{parentId}")
	public ResponseEntity<?> assignStudentToParent(@PathVariable Integer studentId, @PathVariable Integer parentId) {
		try {
			if (studentRepository.existsById(studentId)) {
				if (parentRepository.existsById(parentId)) {
					StudentEntity student = studentDAOImpl.assignToParent(studentId, parentId);
					return new ResponseEntity<StudentEntity>(student, HttpStatus.OK);
				}
				return new ResponseEntity<RESTError>(new RESTError(1, "Parent not found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Student not found"), HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
