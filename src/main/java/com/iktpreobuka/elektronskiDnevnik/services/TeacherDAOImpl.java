package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;

@Service
public class TeacherDAOImpl implements TeacherDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<TeacherEntity> findTeachersBySubject(@PathVariable Integer subject) {
		
		
		String sql = "select t from TeacherEntity t left join fetch t.teacherSubjects tss WHERE tss.subject.id = :subject";
		Query query = em.createQuery(sql);
		query.setParameter("subject", subject);
		
		List<TeacherEntity> retVals = query.getResultList();
		
		return retVals;
		
	}
	
	

}
