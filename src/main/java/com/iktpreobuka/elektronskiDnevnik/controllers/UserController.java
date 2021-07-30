package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.controllers.util.UserCustomValidator;
import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.GradeRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherSubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.UserRepository;
import com.iktpreobuka.elektronskiDnevnik.services.UserDAO;
import com.iktpreobuka.elektronskiDnevnik.services.UserDAOImpl;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDAOImpl userDAOImpl;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ParentRepository parentRepository;

	@Autowired
	private TeacherRepository teacherRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private UserCustomValidator userCustomValidator;

	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;

	@Autowired
	private TeacherSubjectClassroomRepository teacherSubjectClassroomRepository;

	@Autowired
	private GradeRepository gradeRepository;
	
	@Autowired
	private UserDAO userDAO;

	@InitBinder("newUser")
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userCustomValidator);
	}

//	@PostMapping()
//	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity user, BindingResult result) {
//		if (result.hasErrors()) {
//			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
//		}
//		userRepository.save(user);
//
//		return new ResponseEntity<>(user, HttpStatus.CREATED);
//	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/createUser/{roleId}")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntityDTO newUser, @PathVariable Integer roleId,
			BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.NO_CONTENT);
		} else {
//			userCustomValidator.validate(newUser, result);
			if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
				return new ResponseEntity<RESTError>(new RESTError(1, "Password and confirmPassword must be the same"),
						HttpStatus.NOT_ACCEPTABLE);
			}
		}

		UserEntity user = userDAOImpl.createNewUser(newUser, roleId);

		if (user.getRole().equals(roleRepository.findById(11).get())) {
			TeacherEntity teacher = new TeacherEntity();
			teacher.setUsername(newUser.getUsername());
			teacher.setPassword(newUser.getPassword());
			teacher.setName(newUser.getName());
			teacher.setLastname(newUser.getLastname());
			teacher.setEmail(newUser.getEmail());
			teacher.setActive(newUser.isActive());
			teacher.setRole(roleRepository.findById(roleId).get());
			teacherRepository.save(teacher);
			logger.info("Admin with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
					+ teacher.getName() + " has been created!");
		}
		if (user.getRole().equals(roleRepository.findById(12).get())) {
			TeacherEntity teacher = new TeacherEntity();
			teacher.setUsername(newUser.getUsername());
			teacher.setPassword(newUser.getPassword());
			teacher.setName(newUser.getName());
			teacher.setLastname(newUser.getLastname());
			teacher.setEmail(newUser.getEmail());
			teacher.setActive(newUser.isActive());
			teacher.setRole(roleRepository.findById(roleId).get());
			teacherRepository.save(teacher);
			logger.info("Teacher with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
					+ teacher.getName() + " has been created!");
		}
		if (user.getRole().equals(roleRepository.findById(13).get())) {
			StudentEntity student = new StudentEntity();
			student.setUsername(newUser.getUsername());
			student.setPassword(newUser.getPassword());
			student.setName(newUser.getName());
			student.setLastname(newUser.getLastname());
			student.setEmail(newUser.getEmail());
			student.setActive(newUser.isActive());
			student.setRole(roleRepository.findById(roleId).get());
			studentRepository.save(student);
			logger.info("Student with id:" + student.getId() + ", lastname and name: " + student.getLastname() + " "
					+ student.getName() + " has been created!");
		}
		if (user.getRole().equals(roleRepository.findById(14).get())) {
			ParentEntity parent = new ParentEntity();
			parent.setUsername(newUser.getUsername());
			parent.setPassword(newUser.getPassword());
			parent.setName(newUser.getName());
			parent.setLastname(newUser.getLastname());
			parent.setEmail(newUser.getEmail());
			parent.setActive(newUser.isActive());
			parent.setRole(roleRepository.findById(roleId).get());
			parentRepository.save(parent);
			logger.info("Parent with id:" + parent.getId() + ", lastname and name: " + parent.getLastname() + " "
					+ parent.getName() + " has been created!");
		}
		logger.info("User with id:" + user.getId() + ", lastname and name: " + user.getLastname() + " " + user.getName()
				+ " has been created!");
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

	}

	@Secured("ROLE_ADMIN")
	@PatchMapping("/changeUser/userId/{userId}/roleId/{roleId}")
	public ResponseEntity<?> changeUser(@PathVariable Integer userId, @PathVariable Integer roleId,
			@Valid @RequestBody UserEntityDTO changedUser, BindingResult result) {

		if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.NO_CONTENT);
		} else {
			userCustomValidator.validate(changedUser, result);
		}

		UserEntity user = userDAOImpl.changeUser(userId, roleId, changedUser);

		if (user.getRole().equals(roleRepository.findById(11).get())) {
			TeacherEntity teacher = teacherRepository.findByUsername(user.getUsername());
			if (changedUser.getUsername() != null)
				teacher.setUsername(changedUser.getUsername());
			if (changedUser.getPassword() != null)
				teacher.setPassword(changedUser.getPassword());
			if (changedUser.getName() != null)
				teacher.setName(changedUser.getName());
			if (changedUser.getLastname() != null)
				teacher.setLastname(changedUser.getLastname());
			if (changedUser.getEmail() != null)
				teacher.setEmail(changedUser.getEmail());
			if (changedUser.isActive() != false && changedUser.isActive() != true)
				teacher.setActive(changedUser.isActive());
			teacher.setRole(roleRepository.findById(roleId).get());
			teacherRepository.save(teacher);
			logger.info("Admin with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
					+ teacher.getName() + " has been updated!");
		}
		if (user.getRole().equals(roleRepository.findById(12).get())) {
			TeacherEntity teacher = teacherRepository.findByUsername(user.getUsername());
			if (changedUser.getUsername() != null)
				teacher.setUsername(changedUser.getUsername());
			if (changedUser.getPassword() != null)
				teacher.setPassword(changedUser.getPassword());
			if (changedUser.getName() != null)
				teacher.setName(changedUser.getName());
			if (changedUser.getLastname() != null)
				teacher.setLastname(changedUser.getLastname());
			if (changedUser.getEmail() != null)
				teacher.setEmail(changedUser.getEmail());
			if (changedUser.isActive() != false && changedUser.isActive() != true)
				teacher.setActive(changedUser.isActive());
			teacher.setRole(roleRepository.findById(roleId).get());
			teacherRepository.save(teacher);
			logger.info("Teacher with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
					+ teacher.getName() + " has been updated!");
		}
		if (user.getRole().equals(roleRepository.findById(13).get())) {
			StudentEntity student = studentRepository.findByUsername(user.getUsername());
			if (changedUser.getUsername() != null)
				student.setUsername(changedUser.getUsername());
			if (changedUser.getPassword() != null)
				student.setPassword(changedUser.getPassword());
			if (changedUser.getName() != null)
				student.setName(changedUser.getName());
			if (changedUser.getLastname() != null)
				student.setLastname(changedUser.getLastname());
			if (changedUser.getEmail() != null)
				student.setEmail(changedUser.getEmail());
			if (changedUser.isActive() != false && changedUser.isActive() != true)
				student.setActive(changedUser.isActive());
			student.setRole(roleRepository.findById(roleId).get());
			studentRepository.save(student);
			logger.info("Student with id:" + student.getId() + ", lastname and name: " + student.getLastname() + " "
					+ student.getName() + " has been updated!");
		}
		if (user.getRole().equals(roleRepository.findById(14).get())) {
			ParentEntity parent = parentRepository.findByUsername(user.getUsername());
			if (changedUser.getUsername() != null)
				parent.setUsername(changedUser.getUsername());
			if (changedUser.getPassword() != null)
				parent.setPassword(changedUser.getPassword());
			if (changedUser.getName() != null)
				parent.setName(changedUser.getName());
			if (changedUser.getLastname() != null)
				parent.setLastname(changedUser.getLastname());
			if (changedUser.getEmail() != null)
				parent.setEmail(changedUser.getEmail());
			if (changedUser.isActive() != false && changedUser.isActive() != true)
				parent.setActive(changedUser.isActive());
			parent.setRole(roleRepository.findById(roleId).get());
			parentRepository.save(parent);
			logger.info("Parent with id:" + parent.getId() + ", lastname and name: " + parent.getLastname() + " "
					+ parent.getName() + " has been updated!");
		}
		logger.info("User with id:" + user.getId() + ", lastname and name: " + user.getLastname() + " " + user.getName()
				+ " has been updated!");
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/findAllUsers")
	public ResponseEntity<?> findAllUsers() {
		try {
			List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
			if (!users.isEmpty()) {
				return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
			}
			return new ResponseEntity(new RESTError(1, "There are no data"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/findAllAdmin")
	public ResponseEntity<?> findAllAdmins() {
		try {
			List<UserEntity> users = (List<UserEntity>) userRepository.findByRoleId(11);
			if (!users.isEmpty()) {
				return new ResponseEntity<List<UserEntity>>(users, HttpStatus.OK);
			}
			return new ResponseEntity(new RESTError(1, "There are no data"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@DeleteMapping("/deleteUserById/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {

		try {
//				if(userRepository.existsById(userId)) {
			UserEntity user = userRepository.findById(userId).get();
			if (user.getRole().equals(roleRepository.findById(12).get())) {
				TeacherEntity teacher = teacherRepository.findByUsername(user.getUsername());
				TeacherSubject teacherSubject = teacherSubjectRepository.findByTeacherId(teacher.getId());
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
				teacherRepository.delete(teacher);
				userRepository.delete(user);
				logger.info("Teacher with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
						+ teacher.getName() + " has been deleted!");
				logger.info("User with id:" + user.getId() + ", lastname and name: " + user.getLastname() + " " + user.getName()
				+ " has been deleted!");
				
				}
			if(user.getRole().equals(roleRepository.findById(13).get())) {
				StudentEntity student = studentRepository.findByUsername(user.getUsername());
				ParentEntity parent = parentRepository.findByStudentId(student.getId());
				List<GradeEntity> grades = gradeRepository.findByStudentId(student.getId());
				Iterator<GradeEntity> it = grades.iterator();
				while(it.hasNext()) {
					GradeEntity grade = it.next();
					it.remove();
				}
				studentRepository.delete(student);
				userRepository.delete(user);
				userRepository.delete(userRepository.findByUsername(parent.getUsername()));	
				logger.info("Student with id:" + student.getId() + ", lastname and name: " + student.getLastname() + " "
						+ student.getName() + " has been deleted!");
				logger.info("Parent with id:" + parent.getId() + ", lastname and name: " + parent.getLastname() + " "
						+ parent.getName() + " has been deleted!");
				logger.info("User with id:" + user.getId() + ", lastname and name: " + user.getLastname() + " " + user.getName()
				+ " has been deleted!");
				logger.info("Grades: " + grades + " are deleted!");
			}
			if(user.getRole().equals(roleRepository.findById(14).get())) {
				ParentEntity parent = parentRepository.findByUsername(user.getUsername());
				StudentEntity student = studentRepository.findByParentId(parent.getId());
				parentRepository.delete(parent);
				userRepository.delete(user);
				userRepository.delete(userRepository.findByUsername(student.getUsername()));
				logger.info("Parent with id:" + parent.getId() + ", lastname and name: " + parent.getLastname() + " "
						+ parent.getName() + " has been deleted!");
				logger.info("Student with id:" + student.getId() + ", lastname and name: " + student.getLastname() + " "
						+ student.getName() + " has been deleted!");
				logger.info("User with id:" + user.getId() + ", lastname and name: " + user.getLastname() + " " + user.getName()
				+ " has been deleted!");
			}
			return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
//				} return new ResponseEntity<RESTError>(new RESTError(1, "User can not be found!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Secured("ROLE_PARENT")
	@GetMapping("/findByLogin")
	public String getUsername(HttpServletRequest request) {
		return userDAOImpl.getUsername(request);
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/logsInfo")
	public ResponseEntity<?> getLogsInfo(HttpServletRequest request) throws IOException {
		
		BufferedReader in = new BufferedReader(new FileReader("logs//spring-boot-logging.log"));
		
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		while ((line = in.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append("\n");
		}
		
		logger.info(userDAO.getUsername(request) + ": viewed logs.");
		
		return new ResponseEntity<>(stringBuilder.toString(), HttpStatus.OK);
	}
	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" \n"));
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
