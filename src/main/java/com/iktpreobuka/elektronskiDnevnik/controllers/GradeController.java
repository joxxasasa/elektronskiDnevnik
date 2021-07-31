package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.GradeEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.services.EmailService;
import com.iktpreobuka.elektronskiDnevnik.services.GradeDAO;
import com.iktpreobuka.elektronskiDnevnik.services.UserDAO;

@RestController
@RequestMapping("/grades")
public class GradeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GradeRepository gradeRepository;

	@Autowired
	private GradeDAO gradeDAO;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;

	@Secured("ROLE_ADMIN")
	@PostMapping("/createGradeAdmin/studentId/{studentId}/subjectId/{subjectId}")
	public ResponseEntity<?> createGradeAdmin(@PathVariable Integer studentId, @PathVariable Integer subjectId,
			@Valid @RequestBody GradeEntityDTO newGradeDTO, HttpServletRequest request) {

		try {
			if (studentRepository.existsById(studentId)) {
				StudentEntity student = studentRepository.findById(studentId).get();
				TeacherSubject teacherSubject = teacherSubjectRepository.findBySubjectId(subjectId);
				TeacherSubjectClassroom teacherSubjectClassroom = teacherSubjectClassroomRepository
						.findByTeacherSubjectIdAndClassroomId(teacherSubject.getId(), student.getClassroom().getId());
				if (student.getClassroom().equals(teacherSubjectClassroom.getClassroom())) {
					GradeEntity grade = gradeDAO.gradingStudent(studentId, teacherSubjectClassroom.getId(),
							newGradeDTO);
					try {
						emailService.sendGradeInfo(grade);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.info("The grade with id: " + grade.getId() + " has been made and info email has been sent!");
					return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
				}
				return new ResponseEntity<RESTError>(new RESTError(3, "Teacher do not teach in this classroom!"),
						HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Student can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_TEACHER")
	@PostMapping("/createGrade/studentId/{studentId}")
	public ResponseEntity<?> createGrade(@PathVariable Integer studentId,
			@Valid @RequestBody GradeEntityDTO newGradeDTO, HttpServletRequest request) {

		try {
			if (studentRepository.existsById(studentId)) {
				StudentEntity student = studentRepository.findById(studentId).get();
				TeacherEntity teacher = teacherRepository.findByUsername(userDAO.getUsername(request));
				TeacherSubject teacherSubject = teacherSubjectRepository.findByTeacherId(teacher.getId());
//				TeacherSubjectClassroom teacherSubjectClassroom = teacherSubjectClassroomRepository.findByTeacherSubjectId(teacherSubject.getId());
				TeacherSubjectClassroom teacherSubjectClassroom = teacherSubjectClassroomRepository
						.findByTeacherSubjectIdAndClassroomId(teacherSubject.getId(), student.getClassroom().getId());
				if (student.getClassroom().equals(teacherSubjectClassroom.getClassroom())) {
					GradeEntity grade = gradeDAO.gradingStudent(studentId, teacherSubjectClassroom.getId(),
							newGradeDTO);
					try {
						emailService.sendGradeInfo(grade);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					logger.info("The grade with id: " + grade.getId() + " has been made and info email has been sent!");
					return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
				}
				return new ResponseEntity<RESTError>(new RESTError(3, "Teacher do not teach in this classroom!"),
						HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Student can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@Secured("ROLE_TEACHER")/* R A D I  O K */
//	@PostMapping("/createGrade/studentId/{studentId}/teacherSubjectClassroomId/{teacherSubjectClassroomId}")
//	public ResponseEntity<?> createGrade(@PathVariable Integer studentId, @PathVariable Integer teacherSubjectClassroomId, 
//										@Valid @RequestBody GradeEntityDTO newGradeDTO) {
//		
//		
//		
//		try {
//			if (studentRepository.existsById(studentId)) {
//				if (teacherSubjectClassroomRepository.existsById(teacherSubjectClassroomId)) {
//					GradeEntity grade = gradeDAO.gradingStudent(studentId, teacherSubjectClassroomId, newGradeDTO);
//					try {
//						emailService.sendGradeInfo(grade);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					logger.info("The grade with id: " + grade.getId() + " has been made and info email has been sent!");
//					return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
//				}
//				return new ResponseEntity<RESTError>(
//						new RESTError(1, "Teacher which teach subject in classroom can not be found"),
//						HttpStatus.NO_CONTENT);
//			}
//			return new ResponseEntity<RESTError>(new RESTError(1, "Student can not be found"), HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return new ResponseEntity<RESTError>(
//					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
//							+ e.getStackTrace() + ".\n Stack trace:	"),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/deleteGradeAdmin/{gradeId}")
	public ResponseEntity<?> deleteGradeAdmin(@PathVariable Integer gradeId) {
		try {
			if (gradeRepository.existsById(gradeId)) {
				GradeEntity grade = gradeRepository.findById(gradeId).get();
				gradeDAO.deleteGrade(gradeId);
				logger.info("The grade with id: " + gradeId + " has been deleted!");
				return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade can not be found!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_TEACHER")
	@DeleteMapping("/deleteGradeTeacher/{gradeId}")
	public ResponseEntity<?> deleteGradeTeacher(@PathVariable Integer gradeId, HttpServletRequest request) {
		try {
			if (gradeRepository.existsById(gradeId)) {
				TeacherEntity teacher = teacherRepository.findByUsername(userDAO.getUsername(request));
				GradeEntity grade = gradeRepository.findById(gradeId).get();
				if (grade.getTeacherSubjectClassroom().getTeacherSubject().getTeacher().getId()
						.equals(teacher.getId())) {
					gradeDAO.deleteGrade(gradeId);
					logger.info("The grade with id: " + gradeId + " has been deleted!");
					return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
				}
				return new ResponseEntity<RESTError>(new RESTError(3, "Grade is not created by this teacher!"),
						HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade can not be found!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@PutMapping("/updateGradeAdmin/{gradeId}")
	public ResponseEntity<?> updateGradeAdmin(@PathVariable Integer gradeId,
			@RequestBody GradeEntityDTO changedGradeDTO) {
		try {
			if (gradeRepository.existsById(gradeId)) {
				GradeEntity grade = gradeRepository.findById(gradeId).get();
				gradeDAO.updateGrade(gradeId, changedGradeDTO);
				logger.info("The grade with id: " + gradeId + " has been updated!");
				return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade can not be found!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_TEACHER")
	@PutMapping("/updateGradeTeacher/{gradeId}")
	public ResponseEntity<?> updateGradeTeacher(@PathVariable Integer gradeId,
			@RequestBody GradeEntityDTO changedGradeDTO, HttpServletRequest request) {
		try {
			if (gradeRepository.existsById(gradeId)) {
				TeacherEntity teacher = teacherRepository.findByUsername(userDAO.getUsername(request));
				GradeEntity grade = gradeRepository.findById(gradeId).get();
				if (grade.getTeacherSubjectClassroom().getTeacherSubject().getTeacher().getId()
						.equals(teacher.getId())) {
					gradeDAO.updateGrade(gradeId, changedGradeDTO);
					logger.info("The grade with id: " + gradeId + " has been updated!");
					return new ResponseEntity<GradeEntity>(grade, HttpStatus.OK);
				}
				return new ResponseEntity<RESTError>(new RESTError(3, "Grade is not created by this teacher!"),
						HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Grade can not be found!"), HttpStatus.NOT_FOUND);
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

	@Secured("ROLE_ADMIN")
	@GetMapping("/findAllGrades")
	public ResponseEntity<?> getAllGrades() {
		try {
			List<GradeEntity> grades = (List<GradeEntity>) gradeRepository.findAll();
			if (!grades.isEmpty()) {
				return new ResponseEntity<List<GradeEntity>>(grades, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "List of grades is empty!"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@Secured("ROLE_TEACHER")
//	@GetMapping("/findAllGradesByTeacher")
//	public ResponseEntity<?> findAllGradesByTeacher(HttpServletRequest request) {
//		try {
//			TeacherEntity teacher = teacherRepository.findByUsername(userDAO.getUsername(request));
//			List<GradeEntity> grades = ;
//			if (!grades.isEmpty()) {
//				return new ResponseEntity<List<GradeEntity>>(grades, HttpStatus.OK);
//			}
//			return new ResponseEntity<RESTError>(new RESTError(1, "List of grades is empty!"), HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return new ResponseEntity<RESTError>(new RESTError(2, 
//					"While requesting user from DB error ocured. Error message " + e.getMessage() + e.getStackTrace() 
//					+ ".\n Stack trace:	"), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@Secured("ROLE_STUDENT")
//	@GetMapping("/findAllGrades/{studentId}")
//	public ResponseEntity<?> getAllGradesByStudent(@PathVariable Integer studentId) {
//		
//		try {
//			List<GradeEntity> grades = (List<GradeEntity>) gradeRepository.findByStudentId(studentId);
//			if (!grades.isEmpty()) {
//				return new ResponseEntity<List<GradeEntity>>(grades, HttpStatus.OK);
//			}
//			return new ResponseEntity<RESTError>(new RESTError(1, "List of grades is empty!"), HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return new ResponseEntity<RESTError>(new RESTError(2, 
//					"While requesting user from DB error ocured. Error message " + e.getMessage() + e.getStackTrace() 
//					+ ".\n Stack trace:	"), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	@Secured("ROLE_STUDENT")
	@GetMapping("/findAllGradesStudent")
	public ResponseEntity<?> getAllGradesByStudent(HttpServletRequest request) {

		StudentEntity student = studentRepository.findByUsername(userDAO.getUsername(request));

		try {
			List<GradeEntity> grades = (List<GradeEntity>) gradeRepository.findByStudentId(student.getId());
			if (!grades.isEmpty()) {
				return new ResponseEntity<List<GradeEntity>>(grades, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "List of grades is empty!"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_STUDENT")
	@GetMapping("/getReportStudent")
	public ResponseEntity<?> generateGradeReportStudent(HttpServletRequest request) {

		try {
			StudentEntity student = studentRepository.findByUsername(userDAO.getUsername(request));
			return new ResponseEntity<>(gradeDAO.generateGradeReport(student.getId()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("ROLE_PARENT")
	@GetMapping("/findAllGradesParent")
	public ResponseEntity<?> getAllGradesByParent(HttpServletRequest request) {

		ParentEntity parent = parentRepository.findByUsername(userDAO.getUsername(request));
		StudentEntity student = studentRepository.findByParentId(parent.getId());

		try {
			List<GradeEntity> grades = (List<GradeEntity>) gradeRepository.findByStudentId(student.getId());
			if (!grades.isEmpty()) {
				return new ResponseEntity<List<GradeEntity>>(grades, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "List of grades is empty!"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_PARENT")
	@GetMapping("/getReportParent")
	public ResponseEntity<?> generateGradeReportParent(HttpServletRequest request) {

		try {
			ParentEntity parent = parentRepository.findByUsername(userDAO.getUsername(request));
			StudentEntity student = studentRepository.findByParentId(parent.getId());
			return new ResponseEntity<>(gradeDAO.generateGradeReport(student.getId()), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
