package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.UserRepository;
import com.iktpreobuka.elektronskiDnevnik.services.UserDAOImpl;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
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
	
//	@Autowired
//	private UserCustomValidator userCustomValidator;
//
//	@InitBinder
//	protected void initBinder(final WebDataBinder binder) {
//		binder.addValidators(userCustomValidator);
//	}
	
//	@PostMapping()
//	public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity user, BindingResult result) {
//		if (result.hasErrors()) {
//			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
//		}
//		userRepository.save(user);
//
//		return new ResponseEntity<>(user, HttpStatus.CREATED);
//	}

	
	
	@PostMapping("/createUser/{roleId}")
	public ResponseEntity<?> createUser(/*@Valid*/ @RequestBody UserEntityDTO newUser, @PathVariable Integer roleId/*, BindingResult result*/) {
//		if(result.hasErrors()) {
//			return new ResponseEntity<RESTError>( new RESTError(1, "User can not be created"), HttpStatus.NO_CONTENT);
//		}
	
				UserEntity user = userDAOImpl.createNewUser(newUser, roleId);
				
				if(user.getRole().equals(roleRepository.findById(11).get())) {
					TeacherEntity teacher = new TeacherEntity();
					teacher.setUsername(newUser.getUsername());
					teacher.setPassword(newUser.getPassword());
					teacher.setName(newUser.getName());
					teacher.setLastname(newUser.getLastname());
					teacher.setEmail(newUser.getEmail());
					teacher.setActive(newUser.isActive());
					teacher.setRole(roleRepository.findById(roleId).get());
//					return teacher;
					teacherRepository.save(teacher);
				}
				if(user.getRole().equals(roleRepository.findById(12).get())) {
					TeacherEntity teacher = new TeacherEntity();
					teacher.setUsername(newUser.getUsername());
					teacher.setPassword(newUser.getPassword());
					teacher.setName(newUser.getName());
					teacher.setLastname(newUser.getLastname());
					teacher.setEmail(newUser.getEmail());
					teacher.setActive(newUser.isActive());
					teacher.setRole(roleRepository.findById(roleId).get());
//					return teacher;
					teacherRepository.save(teacher);
				}
				if(user.getRole().equals(roleRepository.findById(13).get())) {
					StudentEntity student = new StudentEntity();
					student.setUsername(newUser.getUsername());
					student.setPassword(newUser.getPassword());
					student.setName(newUser.getName());
					student.setLastname(newUser.getLastname());
					student.setEmail(newUser.getEmail());
					student.setActive(newUser.isActive());
					student.setRole(roleRepository.findById(roleId).get());
//					return student;
					studentRepository.save(student);
				}
				if(user.getRole().equals(roleRepository.findById(14).get())) {
					ParentEntity parent = new ParentEntity();
					parent.setUsername(newUser.getUsername());
					parent.setPassword(newUser.getPassword());
					parent.setName(newUser.getName());
					parent.setLastname(newUser.getLastname());
					parent.setEmail(newUser.getEmail());
					parent.setActive(newUser.isActive());
					parent.setRole(roleRepository.findById(roleId).get());
//					return parent;
					parentRepository.save(parent);
				}
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);
			
	
	}

//	private String createErrorMessage(BindingResult result) {
//		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" \n"));
//	}
//	
//
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//		Map<String, String> errors = new HashMap<>();
//		ex.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//		return errors;
//	}

	@PatchMapping("/changeUser/userId/{userId}/roleId/{roleId}")
	public ResponseEntity<?> changeUser(@PathVariable Integer userId, @PathVariable Integer roleId,
			@RequestBody UserEntityDTO changedUser) {

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
		}
		return new ResponseEntity<UserEntity>(user, HttpStatus.OK);

	}

}
