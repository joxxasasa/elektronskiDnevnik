package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class ClassroomDTO {
	
	@NotNull(message = "ClassroomNumber must not be null!")
	@Max(value = 8, message = "ClassroomNumber can be max {value}!")
	private Integer classroomNumber;
	
	@NotNull(message = "ClassroomMark must not be null!")
	private Integer classroomMark;

	public ClassroomDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	
	

}
