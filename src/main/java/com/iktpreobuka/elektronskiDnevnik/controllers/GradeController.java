package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.GradeEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.services.EmailService;
import com.iktpreobuka.elektronskiDnevnik.services.GradeDAO;
import com.iktpreobuka.elektronskiDnevnik.services.GradeDAOImpl;

@RestController
@RequestMapping("/grades")
public class GradeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private GradeDAOImpl gradeDAOImpl;

	@Autowired
	private GradeDAO gradeDAO;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;

	@Autowired
	public EmailService emailService;



	@PostMapping("/createGrade/studentId/{studentId}/teacherSubjectClassroomId/{teacherSubjectClassroomId}")
	public ResponseEntity<?> createGrade(@PathVariable Integer studentId,
			@PathVariable Integer teacherSubjectClassroomId, @Valid @RequestBody GradeEntityDTO newGradeDTO) {
		try {
			if (studentRepository.existsById(studentId)) {
				if (teacherSubjectClassroomRepository.existsById(teacherSubjectClassroomId)) {
					GradeEntity grade = gradeDAOImpl.gradingStudent(studentId, teacherSubjectClassroomId, newGradeDTO);
					try {
						emailService.sendGradeInfo(grade);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.info("The grade with id: " + grade.getId() + " has been made and info email has been sent!");
					return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
				}
				return new ResponseEntity<RESTError>(
						new RESTError(1, "Teacher which teach subject in classroom can not be found"),
						HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Student can not be found"), HttpStatus.NO_CONTENT);
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
