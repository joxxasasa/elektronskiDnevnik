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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.ClassroomDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.services.ClassroomDAOImpl;

@RestController
@RequestMapping("/classrooms")
public class ClassroomController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ClassroomRepository classroomRepository;

	@Autowired
	private ClassroomDAOImpl classroomDAOImpl;
	
	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private GradeRepository gradeRepository;

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
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/deleteClassroom/{classroomId}")
	public ResponseEntity<?> deleteClassroom(@PathVariable Integer classroomId) {
		try {
			
			if (classroomRepository.existsById(classroomId)) {
				ClassroomEntity classroom = classroomRepository.findById(classroomId).get();
				List<TeacherSubjectClassroom> teacherSubjectClassrooms = teacherSubjectClassroomRepository
						.findByClassroomId(classroomId);
				Iterator<TeacherSubjectClassroom> it = teacherSubjectClassrooms.iterator();
				while (it.hasNext()) {
					TeacherSubjectClassroom teacherSubjectClassroom = it.next();
					it.remove();
				}
				List<StudentEntity> students = studentRepository.findByClassroomId(classroomId);
				Iterator<StudentEntity> itt = students.iterator();
				while(itt.hasNext()) {
					StudentEntity student = itt.next();
					List<GradeEntity> grades = gradeRepository.findByStudentId(student.getId());
					Iterator<GradeEntity> ittt = grades.iterator();
					while(ittt.hasNext()) {
						GradeEntity grade = ittt.next();
						ittt.remove();
					}
					itt.remove();
					logger.info("Grades: " + grades + " are deleted!");
					logger.info("Students: " + students + " are deleted!");
				}
				classroomRepository.delete(classroom);
				logger.info("Classroom with id:" + classroom.getId() + " has been deleted!");
				
				return new ResponseEntity<ClassroomEntity>(classroom, HttpStatus.OK);
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
