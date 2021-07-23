package com.iktpreobuka.elektronskiDnevnik.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "role")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class RoleEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private List<UserEntity> users = new ArrayList<>();

	public RoleEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public RoleEntity(Integer id, String name, List<UserEntity> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	
}
