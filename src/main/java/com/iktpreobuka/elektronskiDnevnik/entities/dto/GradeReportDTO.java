package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import java.time.LocalDate;

import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;

public class GradeReportDTO {
	
	private Integer value;
	
	private SubjectEntity subject;
	
	private LocalDate date;

	public GradeReportDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	

}
