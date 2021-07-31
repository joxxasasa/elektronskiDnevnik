package com.iktpreobuka.elektronskiDnevnik.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.StudentRepository;

@Service
public class StudentDAOImpl implements StudentDAO{
	
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private ClassroomRepository classroomRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	public StudentEntity assignToClassroom(Integer studentId, Integer classroomId) {
		
				StudentEntity student = studentRepository.findById(studentId).get();
				ClassroomEntity classroom = classroomRepository.findById(classroomId).get();
				student.setClassroom(classroom);
				studentRepository.save(student);
				return student;
			
	}
	
	public StudentEntity assignToParent(Integer studentId, Integer parentId) {
		StudentEntity student = studentRepository.findById(studentId).get();
		ParentEntity parent = parentRepository.findById(parentId).get();
		student.setParent(parent);
		studentRepository.save(student);
		return student;
	}
	
	public StudentEntity createStudent(UserEntity newUser, Integer roleId) {
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
		return student;
	}
	
	public StudentEntity changeStudent(UserEntity user, UserEntityDTO changedUser, Integer roleId) {
		StudentEntity student = studentRepository.findByUsername(user.getUsername());
		if (changedUser.getUsername() != null)
			student.setUsername(changedUser.getUsername());
		if (changedUser.getPassword() !=null && changedUser.getPassword() == changedUser.getConfirmPassword())
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
		return student;
	}
	
	public StudentEntity changeClassroom(StudentEntity student, Integer classroomId) {
		ClassroomEntity classroom = classroomRepository.findById(classroomId).get();
		student.setClassroom(classroom);
		studentRepository.save(student);
		logger.info("Student with id:" + student.getId() + ", lastname and name: " + student.getLastname() + " "
				+ student.getName() + " has changed the classroom");
		return student;
		
	}

}
