package com.iktpreobuka.elektronskiDnevnik.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;

@RestController
@RequestMapping("/parents")
public class ParentController {
	
	@Autowired
	private ParentRepository parentRepository;
	
	@Secured("ROLE_ADMIN")
	@GetMapping("/findByStudentId/{studentId}")
	public ParentEntity findByStudentId(@PathVariable Integer studentId) {
		return parentRepository.findByStudentId(studentId);
	}

}
