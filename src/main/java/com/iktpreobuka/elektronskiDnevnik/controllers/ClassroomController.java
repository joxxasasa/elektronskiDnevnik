package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.ClassroomDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.services.ClassroomDAOImpl;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private ClassroomDAOImpl classroomDAOImpl;

	@Secured("ROLE_ADMIN")
	@PostMapping("/createClassroom")
	public ResponseEntity<?> createClassroom(@Valid @RequestBody ClassroomDTO newClassroomDTO) {
		try {
			if (newClassroomDTO != null) {
				ClassroomEntity classroom = classroomDAOImpl.createNewClassroom(newClassroomDTO);
				return new ResponseEntity<ClassroomEntity>(classroom, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Classroom can not be crated"),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/changeClassroom/{classroomId}")
	public ResponseEntity<?> changeClassroom(@PathVariable Integer classroomId,
			@RequestBody ClassroomDTO changedClassroomDTO) {
		try {
			if (changedClassroomDTO != null) {
				ClassroomEntity classroom = classroomDAOImpl.changeClassroom(classroomId, changedClassroomDTO);
				return new ResponseEntity<ClassroomEntity>(classroom, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Classroom can not be changed"),
					HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/findAllClassrooms")
	public ResponseEntity<?> findAllClassrooms() {
		try {
			List<ClassroomEntity> classrooms = (List<ClassroomEntity>) classroomRepository.findAll();
			if (!classrooms.isEmpty()) {
				return new ResponseEntity<List<ClassroomEntity>>(classrooms, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Classrooms can not be found"),
					HttpStatus.NO_CONTENT);
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
