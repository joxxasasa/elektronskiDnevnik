package com.iktpreobuka.elektronskiDnevnik.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "parent")
public class ParentEntity extends UserEntity{
	
	@JsonManagedReference
	@OneToOne(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, orphanRemoval = true)
	private StudentEntity student;

	public ParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParentEntity(Integer id, String username, String password, String name, String lastname, String email,
			boolean isActive, RoleEntity role, StudentEntity student) {
		super(id, username, password, name, lastname, email, isActive, role);
		this.student = student;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	
}
