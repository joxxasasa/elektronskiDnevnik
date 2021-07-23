package com.iktpreobuka.elektronskiDnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.GradeEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.services.GradeDAOImpl;

@RestController
@RequestMapping("/grades")
public class GradeController {
	
	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private GradeDAOImpl gradeDAOImpl;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;
	
	@PostMapping("/createGrade/studentId/{studentId}/teacherSubjectClassroomId/{teacherSubjectClassroomId}")
	public ResponseEntity<?> createGrade(@PathVariable Integer studentId, @PathVariable Integer teacherSubjectClassroomId,
											@RequestBody GradeEntityDTO newGradeDTO) {
		try {
			if (studentRepository.existsById(studentId)) {
				if(teacherSubjectClassroomRepository.existsById(teacherSubjectClassroomId)) {
				GradeEntity grade = gradeDAOImpl.gradingStudent(studentId, teacherSubjectClassroomId, newGradeDTO);
				return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
				} return new ResponseEntity<RESTError>(new RESTError(1, "Teacher which teach subject in classroom can not be found"),
						HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Student can not be found"),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
