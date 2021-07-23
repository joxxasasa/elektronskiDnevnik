package com.iktpreobuka.elektronskiDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "teacher")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TeacherEntity extends UserEntity{
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<TeacherSubject> teacherSubjects;

	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherEntity(Integer id, String username, String password, String name, String lastname, String email,
			boolean isActive, RoleEntity role, List<TeacherSubject> teacherSubjects) {
		super(id, username, password, name, lastname, email, isActive, role);
		this.teacherSubjects = teacherSubjects;
	}

	public List<TeacherSubject> getTeacherSubjects() {
		return teacherSubjects;
	}

	public void setTeacherSubjects(List<TeacherSubject> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}
	
	
	

}
