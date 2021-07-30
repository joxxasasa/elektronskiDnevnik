package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import java.time.LocalDate;

import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;

public class GradeItem {
	
	private LocalDate date;
	
	private Integer value;
	
	private SubjectEntity subject;

	public GradeItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

}
