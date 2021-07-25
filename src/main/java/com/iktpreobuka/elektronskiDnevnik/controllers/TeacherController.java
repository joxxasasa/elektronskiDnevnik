package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.services.TeacherDAO;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	
	@Autowired
	private TeacherDAO teacherDAO;
	
	@GetMapping("/")
	public List<TeacherEntity> getAllTeachers() {
		return (List<TeacherEntity>) teacherRepository.findAll();
	}
	
	@GetMapping("/findById/{teacherId}")
	public ResponseEntity<?> getById(@PathVariable Integer teacherId) {
		try {
			if (teacherRepository.existsById(teacherId)) {
				TeacherEntity teacher = teacherRepository.findById(teacherId).get();
				return new ResponseEntity<TeacherEntity>(teacher, HttpStatus.OK);
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
	
	@GetMapping("/findAllBySubject/{subject}")
	public ResponseEntity<?> findAllBySubjectId(@PathVariable Integer subject) {
		try {
			if (subjectRepository.existsById(subject)) {
				List<TeacherEntity> teachersBySubject = teacherDAO.findTeachersBySubject(subject);
				return new ResponseEntity<List<TeacherEntity>>(teachersBySubject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Teachers can not be found"),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage() +
					".\n Stack trace:	"+ e.getStackTrace()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
