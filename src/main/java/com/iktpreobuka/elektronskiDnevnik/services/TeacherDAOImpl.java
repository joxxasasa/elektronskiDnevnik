package com.iktpreobuka.elektronskiDnevnik.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.UserEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.UserEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.RoleRepository;
import com.iktpreobuka.elektronskiDnevnik.repositories.TeacherRepository;

@Service
public class TeacherDAOImpl implements TeacherDAO{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private TeacherRepository teacherRepository;
	
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
	
	@Override
	public List<TeacherEntity> findTeachersBySubjectAndClassroom(Integer subject, Integer classroom) {
		String sql = "select t from TeacherEntity t left join fetch t.teacherSubjects tss left join fetch tss.teacherSubjectClassrooms tsc WHERE tss.subject.id = :subject and tsc.classroom.id = :classroom";
		Query query = em.createQuery(sql);
		query.setParameter("subject", subject);
		query.setParameter("classroom", classroom);
		
		List<TeacherEntity> retVals = query.getResultList();
		
		return retVals;
	}
	
	public TeacherEntity createTeacher(UserEntityDTO newUser, Integer roleId) {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setUsername(newUser.getUsername());
		teacher.setPassword(newUser.getPassword());
		teacher.setName(newUser.getName());
		teacher.setLastname(newUser.getLastname());
		teacher.setEmail(newUser.getEmail());
		teacher.setActive(newUser.isActive());
		teacher.setRole(roleRepository.findById(roleId).get());
		teacherRepository.save(teacher);
		logger.info("Teacher with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
				+ teacher.getName() + " has been created!");
		return teacher;
	}
	
	public TeacherEntity createAdmin(UserEntityDTO newUser, Integer roleId) {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setUsername(newUser.getUsername());
		teacher.setPassword(newUser.getPassword());
		teacher.setName(newUser.getName());
		teacher.setLastname(newUser.getLastname());
		teacher.setEmail(newUser.getEmail());
		teacher.setActive(newUser.isActive());
		teacher.setRole(roleRepository.findById(roleId).get());
		teacherRepository.save(teacher);
		logger.info("Admin with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
				+ teacher.getName() + " has been created!");
		return teacher;
	}
	
	public TeacherEntity changeAdmin(UserEntity user, UserEntityDTO changedUser, Integer roleId) {
		TeacherEntity teacher = teacherRepository.findByUsername(user.getUsername());
		if (changedUser.getUsername() != null)
			teacher.setUsername(changedUser.getUsername());
		if (changedUser.getPassword() != null)
			teacher.setPassword(changedUser.getPassword());
		if (changedUser.getName() != null)
			teacher.setName(changedUser.getName());
		if (changedUser.getLastname() != null)
			teacher.setLastname(changedUser.getLastname());
		if (changedUser.getEmail() != null)
			teacher.setEmail(changedUser.getEmail());
		if (changedUser.isActive() != false && changedUser.isActive() != true)
			teacher.setActive(changedUser.isActive());
		teacher.setRole(roleRepository.findById(roleId).get());
		teacherRepository.save(teacher);
		logger.info("Admin with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
				+ teacher.getName() + " has been updated!");
		return teacher;
	}
	
	public TeacherEntity changeTeacher(UserEntity user, UserEntityDTO changedUser, Integer roleId) {
		TeacherEntity teacher = teacherRepository.findByUsername(user.getUsername());
		if (changedUser.getUsername() != null)
			teacher.setUsername(changedUser.getUsername());
		if (changedUser.getPassword() !=null && changedUser.getPassword() == changedUser.getConfirmPassword())
			teacher.setPassword(changedUser.getPassword());
		if (changedUser.getName() != null)
			teacher.setName(changedUser.getName());
		if (changedUser.getLastname() != null)
			teacher.setLastname(changedUser.getLastname());
		if (changedUser.getEmail() != null)
			teacher.setEmail(changedUser.getEmail());
		if (changedUser.isActive() != false && changedUser.isActive() != true)
			teacher.setActive(changedUser.isActive());
		teacher.setRole(roleRepository.findById(roleId).get());
		teacherRepository.save(teacher);
		logger.info("Teacher with id:" + teacher.getId() + ", lastname and name: " + teacher.getLastname() + " "
				+ teacher.getName() + " has been updated!");
		return teacher;
	}

}
