package com.iktpreobuka.elektronskiDnevnik.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class SubjectEntityDTO {
	
	@NotBlank(message = "Subject name must be provided!")
	@Column(nullable = false, unique = true)
	private String name;
	
	private Integer weekFond;

	public SubjectEntityDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	
}
