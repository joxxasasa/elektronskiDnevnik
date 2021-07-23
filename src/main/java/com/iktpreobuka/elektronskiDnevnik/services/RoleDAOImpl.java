package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;

@Service
public class RoleDAOImpl implements RoleDAO{
	
	@Autowired
	private RoleRepository roleRepository;
	
	public RoleEntity createRole(RoleEntity newRole) {
		RoleEntity role = new RoleEntity();
		role.setName(newRole.getName());
		roleRepository.save(role);
		return role;
	}
	
	

}
