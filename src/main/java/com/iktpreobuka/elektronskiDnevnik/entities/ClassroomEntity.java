package com.iktpreobuka.elektronskiDnevnik.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "classroom")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ClassroomEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull(message = "ClassroomNumber must not be null!")
	@Max(value = 8, message = "ClassroomNumber can be max {value}!")
	private Integer classroomNumber;
	
	@NotNull(message = "ClassroomMark must not be null!")
	private Integer classroomMark;
	
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<StudentEntity> students;
	
	
	@OneToMany(mappedBy = "classroom", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TeacherSubjectClassroom> teacherSubjectClassrooms;
	
	

	public ClassroomEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClassroomEntity(Integer id, Integer classroomNumber, Integer classroomMark, List<StudentEntity> students,
			List<TeacherSubjectClassroom> teacherSubjectClassrooms) {
		super();
		this.id = id;
		this.classroomNumber = classroomNumber;
		this.classroomMark = classroomMark;
		this.students = students;
		this.teacherSubjectClassrooms = teacherSubjectClassrooms;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getClassroomNumber() {
		return classroomNumber;
	}

	public void setClassroomNumber(Integer classroomNumber) {
		this.classroomNumber = classroomNumber;
	}

	public Integer getClassroomMark() {
		return classroomMark;
	}

	public void setClassroomMark(Integer classroomMark) {
		this.classroomMark = classroomMark;
	}

	public List<StudentEntity> getStudents() {
		return students;
	}

	public void setStudents(List<StudentEntity> students) {
		this.students = students;
	}

	public List<TeacherSubjectClassroom> getTeacherSubjectClassrooms() {
		return teacherSubjectClassrooms;
	}

	public void setTeacherSubjectClassrooms(List<TeacherSubjectClassroom> teacherSubjectClassrooms) {
		this.teacherSubjectClassrooms = teacherSubjectClassrooms;
	}

	
	
	
	

}
