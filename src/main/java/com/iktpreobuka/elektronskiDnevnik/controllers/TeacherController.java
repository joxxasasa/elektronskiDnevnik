package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
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
	
	@Autowired
	private ClassroomRepository classroomRepository;
	
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
	
	@Secured("ROLE_TEACHER")
	@GetMapping("/findAllBySubject/{subjectId}")
	public ResponseEntity<?> findAllBySubjectId(@PathVariable Integer subjectId) {
		try {
			if (subjectRepository.existsById(subjectId)) {
//				List<TeacherEntity> teachersBySubject = teacherDAO.findTeachersBySubject(subject);
				List<TeacherEntity> teachersBySubject = teacherRepository.findByTeacherSubjectsSubjectId(subjectId);
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
	
//	@Secured("ROLE_TEACHER")
//	@GetMapping("/findAllBySubjectAndClassroom/{subject}/{classroom}")
//	public ResponseEntity<?> findAllBySubjectAndClassroom(@PathVariable Integer subject, @PathVariable Integer classroom) {
//		try {
//			if (subjectRepository.existsById(subject)) {
//				if(classroomRepository.existsById(classroom)) {
//				List<TeacherEntity> teachersBySubject = teacherDAO.findTeachersBySubjectAndClassroom(subject, classroom);
//				return new ResponseEntity<List<TeacherEntity>>(teachersBySubject, HttpStatus.OK);
//				} return new ResponseEntity<RESTError>(new RESTError(3, "Classroom can not be found"), HttpStatus.NOT_FOUND);
//			}
//			return new ResponseEntity<RESTError>(new RESTError(1, "Subject can not be found"),
//					HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return new ResponseEntity<RESTError>(
//					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage() +
//					".\n Stack trace:	"+ e.getStackTrace()), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@Secured("ROLE_TEACHER")
	@GetMapping("/findAllBySubjectAndClassroom/{subjectId}/{classroomId}")
	public ResponseEntity<?> findAllBySubjectAndClassroom(@PathVariable Integer subjectId, @PathVariable Integer classroomId) {
		try {
			if (subjectRepository.existsById(subjectId)) {
				if(classroomRepository.existsById(classroomId)) {
				List<TeacherEntity> teachersBySubject = teacherRepository.findByTeacherSubjectsSubjectId(subjectId);
				
				return new ResponseEntity<List<TeacherEntity>>(teachersBySubject, HttpStatus.OK);
				} return new ResponseEntity<RESTError>(new RESTError(3, "Classroom can not be found"), HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject can not be found"),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage() +
					".\n Stack trace:	"+ e.getStackTrace()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@Secured("ROLE_TEACHER")
//	@GetMapping("/findAllBySubjectAndClassroom/{subjectId}/{classroomId}")
//	public List<TeacherEntity> findAllBySubjectAndClassroom(@PathVariable Integer subjectId, @PathVariable Integer classroomId) {
//		return null;
//	}
}
