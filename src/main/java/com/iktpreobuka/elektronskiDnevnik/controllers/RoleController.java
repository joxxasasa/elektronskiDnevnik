package com.iktpreobuka.elektronskiDnevnik.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskiDnevnik.services.RoleDAOImpl;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleDAOImpl roleDAOImpl;
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/createRole")
	public ResponseEntity<?> createRole(@RequestBody RoleEntity newRole) {
		try {
			if (newRole != null) {
				RoleEntity role = roleDAOImpl.createRole(newRole);
				return new ResponseEntity<RoleEntity>(role, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Role can not be crated"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
}
