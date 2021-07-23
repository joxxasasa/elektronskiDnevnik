package com.iktpreobuka.elektronskiDnevnik.controllers.util;

public class RESTError {
	
	private Integer id;
	
	private String message;

	public RESTError() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RESTError(Integer id, String message) {
		super();
		this.id = id;
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
