package com.iktpreobuka.elektronskiDnevnik.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "grade")
public class GradeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private LocalDate date;
	
	private Integer semester;
	
	private Integer value;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student")
	private StudentEntity student;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherSubjectClassroom")
	private TeacherSubjectClassroom teacherSubjectClassroom;

	public GradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradeEntity(Integer id, LocalDate date, Integer semester, Integer value, StudentEntity student,
			TeacherSubjectClassroom teacherSubjectClassroom) {
		super();
		this.id = id;
		this.date = date;
		this.semester = semester;
		this.value = value;
		this.student = student;
		this.teacherSubjectClassroom = teacherSubjectClassroom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public StudentEntity getStudent() {
		return student;
	}

	public void setStudent(StudentEntity student) {
		this.student = student;
	}

	public TeacherSubjectClassroom getTeacherSubjectClassroom() {
		return teacherSubjectClassroom;
	}

	public void setTeacherSubjectClassroom(TeacherSubjectClassroom teacherSubjectClassroom) {
		this.teacherSubjectClassroom = teacherSubjectClassroom;
	}
	
	
	

}
