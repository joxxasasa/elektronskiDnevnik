package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.SubjectEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.services.SubjectDAOImpl;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SubjectDAOImpl subjectDAOImpl;

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	
	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;
	
	@Autowired
	private GradeRepository gradeRepository;

	@Secured("ROLE_ADMIN")
	@PostMapping("/createSubject")
	public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectEntityDTO newSubjectDTO) {

		try {
			if (newSubjectDTO != null) {
				SubjectEntity subject = new SubjectEntity();
				subject.setName(newSubjectDTO.getName());
				subject.setWeekFond(newSubjectDTO.getWeekFond());
				subjectDAOImpl.createNewSubject(subject);
				logger.info("Subject with id:" + subject.getId() + " and name: " + subject.getName() + " "
						+ " has been created!");
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject can not be crated"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/findByName/{name}")
	public ResponseEntity<?> findSubjectByName(@PathVariable String name) {
		try {
			if (name != null) {
				SubjectEntity subject = subjectRepository.findByName(name);
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/findAll")
	public ResponseEntity<?> findAllSubjects() {
		try {
			List<SubjectEntity> subjects = (List<SubjectEntity>) subjectRepository.findAll();
			if (!subjects.isEmpty()) {
				return new ResponseEntity<List<SubjectEntity>>(subjects, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subjects can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/changeSubject/{name}")
	public ResponseEntity<?> changeSubject(@PathVariable String name, @RequestBody SubjectEntity changedSubject) {
		try {

			if (subjectRepository.existsById(subjectRepository.findByName(name).getId())) {
				SubjectEntity subject = subjectDAOImpl.changeSubject(name, changedSubject);
				subjectRepository.save(subject);
				logger.info("Subject with id:" + subject.getId() + " and name: " + subject.getName() + " "
						+ " has been updated!");
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subjects can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/deleteSubject/{name}")
	public ResponseEntity<?> deleteSubject(@PathVariable String name) {
		try {
			if (subjectRepository.existsById(subjectRepository.findByName(name).getId())) {
				SubjectEntity subject = subjectRepository.findByName(name);
				TeacherSubject teacherSubject = teacherSubjectRepository.findBySubjectId(subject.getId());
				List<TeacherSubjectClassroom> teacherSubjectClassrooms = teacherSubjectClassroomRepository
						.findByTeacherSubjectId(teacherSubject.getId());
				Iterator<TeacherSubjectClassroom> it = teacherSubjectClassrooms.iterator();
				while (it.hasNext()) {
					TeacherSubjectClassroom teacherSubjectClassroom = it.next();
					List<GradeEntity> grades = gradeRepository
							.findByTeacherSubjectClassroomId(teacherSubjectClassroom.getId());
					Iterator<GradeEntity> itt = grades.iterator();
					while (itt.hasNext()) {
						GradeEntity grade = itt.next();
						itt.remove();
					}
					it.remove();
					logger.info("Grades: " + grades + " are deleted!");
				}
				teacherSubjectRepository.delete(teacherSubject);
				subjectRepository.delete(subject);
				logger.info("Subject with id:" + subject.getId() + " and name: " + subject.getName() + " "
						+ " has been deleted!");
							
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not found!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
