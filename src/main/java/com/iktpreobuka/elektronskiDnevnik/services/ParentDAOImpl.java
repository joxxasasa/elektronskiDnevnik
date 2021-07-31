package com.iktpreobuka.elektronskiDnevnik.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.ParentEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.ParentRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;

@Service
public class ParentDAOImpl implements ParentDAO{
	
private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private ParentRepository parentRepository;
	
	public ParentEntity createParent(UserEntity newUser, Integer roleId) {
		ParentEntity parent = new ParentEntity();
		parent.setUsername(newUser.getUsername());
		parent.setPassword(newUser.getPassword());
		parent.setName(newUser.getName());
		parent.setLastname(newUser.getLastname());
		parent.setEmail(newUser.getEmail());
		parent.setActive(newUser.isActive());
		parent.setRole(roleRepository.findById(roleId).get());
		parentRepository.save(parent);
		logger.info("Parent with id:" + parent.getId() + ", lastname and name: " + parent.getLastname() + " "
				+ parent.getName() + " has been created!");
		return parent; 
	}
	
	public ParentEntity changeParent(UserEntity user, UserEntityDTO changedUser, Integer roleId) {
		ParentEntity parent = parentRepository.findByUsername(user.getUsername());
		if (changedUser.getUsername() != null)
			parent.setUsername(changedUser.getUsername());
		if (changedUser.getPassword() !=null && changedUser.getPassword() == changedUser.getConfirmPassword())
			parent.setPassword(changedUser.getPassword());
		if (changedUser.getName() != null)
			parent.setName(changedUser.getName());
		if (changedUser.getLastname() != null)
			parent.setLastname(changedUser.getLastname());
		if (changedUser.getEmail() != null)
			parent.setEmail(changedUser.getEmail());
		if (changedUser.isActive() != false && changedUser.isActive() != true)
			parent.setActive(changedUser.isActive());
		parent.setRole(roleRepository.findById(roleId).get());
		parentRepository.save(parent);
		logger.info("Parent with id:" + parent.getId() + ", lastname and name: " + parent.getLastname() + " "
				+ parent.getName() + " has been updated!");
		return parent;
	}

}
