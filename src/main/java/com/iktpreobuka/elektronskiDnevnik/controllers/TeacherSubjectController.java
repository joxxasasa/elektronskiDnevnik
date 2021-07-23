package com.iktpreobuka.elektronskiDnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskiDnevnik.services.TeacherSubjectDAOImpl;

@RestController
@RequestMapping("/teacherSubjects")
public class TeacherSubjectController {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private TeacherSubjectDAOImpl teacherSubjectDAOImpl;
	
	@PostMapping("/assignSubjectToTeacher/teacherId/{teacherId}/subjectId/{subjectId}")
	public ResponseEntity<?> assignSubjectToTeacher(@PathVariable Integer teacherId, @PathVariable Integer subjectId) {
		try {
			if (teacherRepository.existsById(teacherId)) {
				if(subjectRepository.existsById(subjectId)) {
				TeacherSubject teacherSubject = teacherSubjectDAOImpl.assignSubjectTeacher(teacherId, subjectId);
				return new ResponseEntity<TeacherSubject>(teacherSubject, HttpStatus.OK);
				} return new ResponseEntity<RESTError>(new RESTError(1, "Subject can not be found"),
						HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Teacher can not be found"),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
