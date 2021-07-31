package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;

public interface StudentDAO {
	
	public StudentEntity assignToClassroom(Integer studentId, Integer classroomId);
	public StudentEntity assignToParent(Integer studentId, Integer parentId);
	public StudentEntity createStudent(UserEntity newUser, Integer roleId);
	public StudentEntity changeStudent(UserEntity user, UserEntityDTO changedUser, Integer roleId);
	public StudentEntity changeClassroom(StudentEntity student, Integer classroomId);

}
