package com.iktpreobuka.elektronskiDnevnik.controllers;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskiDnevnik.services.RoleDAO;
import com.iktpreobuka.elektronskiDnevnik.services.RoleDAOImpl;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleDAOImpl roleDAOImpl;
	
	
//	protected List<RoleEntity> initializeRole() {
//		List<RoleEntity> roles = new ArrayList<>();
//		if (roles.size() == 0) {
//			roles.add(new RoleEntity(1, "ADMIN", null));
//			roles.add(new RoleEntity(2, "TEACHER", null));
//			roles.add(new RoleEntity(3, "STUDENT", null));
//			roles.add(new RoleEntity(4, "PARENT", null));
//		}
//		return roles;
//	}
	
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
