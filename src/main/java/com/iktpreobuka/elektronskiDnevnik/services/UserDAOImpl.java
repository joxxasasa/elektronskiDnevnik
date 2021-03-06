package com.iktpreobuka.elektronskiDnevnik.services;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.RoleEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.UserRepository;

@Service
public class UserDAOImpl implements UserDAO{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public UserEntity createNewUser(UserEntityDTO newUserDTO, Integer roleId) {
		UserEntity user = new UserEntity();
		
		user.setUsername(newUserDTO.getUsername());
		user.setPassword(newUserDTO.getPassword());
		user.setName(newUserDTO.getName());
		user.setLastname(newUserDTO.getLastname());
		user.setEmail(newUserDTO.getEmail());
		user.setActive(newUserDTO.isActive());
		RoleEntity role = roleRepository.findById(roleId).get();
		user.setRole(role);
		userRepository.save(user);
			
		return user;
	}
	
	public UserEntity changeUser(Integer userId, Integer roleId, UserEntityDTO changedUser) {

		UserEntity user = userRepository.findById(userId).get();
		if(changedUser.getUsername() != null) {
			user.setUsername(changedUser.getUsername());
		}
		if(changedUser.getPassword() !=null && changedUser.getPassword() == changedUser.getConfirmPassword()) {
			user.setPassword(changedUser.getPassword());
		}
		if(changedUser.getName() != null) {
			user.setName(changedUser.getName());
		}
		if(changedUser.getLastname() != null) {
			user.setLastname(changedUser.getLastname());
		}
		if(changedUser.getEmail() != null) {
			user.setEmail(changedUser.getEmail());
		}
		if(changedUser.isActive() != false && changedUser.isActive() != true) {
			user.setActive(changedUser.isActive());
		}
		if(roleId != null) {
			user.setRole(roleRepository.findById(roleId).get());
		}
		userRepository.save(user);
		return user;
	}
	
	public String getUsername(HttpServletRequest request) {
		return request.getUserPrincipal().getName();
	}

}
