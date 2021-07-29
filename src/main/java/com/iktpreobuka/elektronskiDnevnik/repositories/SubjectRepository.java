package com.iktpreobuka.elektronskiDnevnik.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;

public interface SubjectRepository extends CrudRepository<SubjectEntity, Integer>{
	
	public SubjectEntity findByName(String name);
	

}
