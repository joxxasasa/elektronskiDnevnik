package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import java.util.List;

public class GradeReportDTO {
	
	private Double averageSucces;
	
	private List<GradeItem> grades;

	public GradeReportDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Double getAverageSucces() {
		return averageSucces;
	}

	public void setAverageSucces(Double averageSucces) {
		this.averageSucces = averageSucces;
	}

	public List<GradeItem> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeItem> grades) {
		this.grades = grades;
	}

}
