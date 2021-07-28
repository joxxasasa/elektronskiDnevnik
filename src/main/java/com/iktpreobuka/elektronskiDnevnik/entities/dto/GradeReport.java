package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import java.util.List;

public class GradeReport {
	
	private List<GradeReportDTO> grades;
	
	private Double averageSucces;

	public GradeReport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<GradeReportDTO> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeReportDTO> grades) {
		this.grades = grades;
	}

	public Double getAverageSucces() {
		return averageSucces;
	}

	public void setAverageSucces(Double averageSucces) {
		this.averageSucces = averageSucces;
	}
	
	

}
