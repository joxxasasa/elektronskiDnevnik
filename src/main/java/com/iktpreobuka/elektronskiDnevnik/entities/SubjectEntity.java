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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "subject")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubjectEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	private Integer weekFond;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<TeacherSubject> teacherSubjects;

	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectEntity(Integer id, String name, Integer weekFond, List<TeacherSubject> teacherSubjects) {
		super();
		this.id = id;
		this.name = name;
		this.weekFond = weekFond;
		this.teacherSubjects = teacherSubjects;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeekFond() {
		return weekFond;
	}

	public void setWeekFond(Integer weekFond) {
		this.weekFond = weekFond;
	}

	public List<TeacherSubject> getTeacherSubjects() {
		return teacherSubjects;
	}

	public void setTeacherSubjects(List<TeacherSubject> teacherSubjects) {
		this.teacherSubjects = teacherSubjects;
	}
	
	
	
}
