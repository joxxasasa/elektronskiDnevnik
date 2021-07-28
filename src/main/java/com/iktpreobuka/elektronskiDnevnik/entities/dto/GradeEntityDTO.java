package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class GradeEntityDTO {
	
	@NotNull(message = "Semester number must be provided!")
	@Max(value = 2, message = "Semester can be max {value}!")
	private Integer semester;
	
	@NotNull(message = "Grade value must be provided!")
	@Max(value = 5, message = "Max value can be max {value}!")
	@Min(value = 1, message = "Min value can be min {value}!")
	private Integer value;

	public GradeEntityDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	

}
