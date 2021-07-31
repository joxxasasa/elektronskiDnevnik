package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.List;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;

public interface TeacherDAO {
	
	public List<TeacherEntity> findTeachersBySubject(Integer subject);

	List<TeacherEntity> findTeachersBySubjectAndClassroom(Integer subject, Integer classroom);
	public TeacherEntity createTeacher(UserEntityDTO newUser, Integer roleId);
	public TeacherEntity createAdmin(UserEntityDTO newUser, Integer roleId);
	public TeacherEntity changeAdmin(UserEntity user, UserEntityDTO changedUser, Integer roleId);
	public TeacherEntity changeTeacher(UserEntity user, UserEntityDTO changedUser, Integer roleId);

}
