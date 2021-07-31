package com.iktpreobuka.elektronskiDnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.services.TeacherSubjectClassroomDAOImpl;

@RestController
@RequestMapping("/teacherSubjectClassroom")
public class TeacherSubjectClassroomController {

	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;
	
	@Autowired
	private TeacherSubjectClassroomDAOImpl teacherSubjectClassroomDAOImpl;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	
	@Autowired
	private ClassroomRepository classroomRepository;
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/assignTeacherWithSubjectToClassroom/teacherSubjectId/{teacherSubjectId}/classroomId/{classroomId}")
	public ResponseEntity<?> assignTeacherWithSubjectToClassroom(@PathVariable Integer teacherSubjectId, 
																	@PathVariable Integer classroomId) {
		try {
			if (teacherSubjectRepository.existsById(teacherSubjectId)) {
				if(classroomRepository.existsById(classroomId)) {
				TeacherSubjectClassroom teacherSubjectClassroom = teacherSubjectClassroomDAOImpl
						.assignTeacherSubjectToClassroom(teacherSubjectId, classroomId);
				return new ResponseEntity<TeacherSubjectClassroom>(teacherSubjectClassroom, HttpStatus.OK);
				} return new ResponseEntity<RESTError>(new RESTError(1, "Classroom can not be found"),
						HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher which teach that subject can not be found"),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
