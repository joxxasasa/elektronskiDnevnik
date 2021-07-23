package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskiDnevnik.repositories.SubjectRepository;

@Service
public class SubjectDAOImpl implements SubjectDAO{
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	public SubjectEntity createNewSubject(SubjectEntity subject) {
//		SubjectEntity subject = new SubjectEntity();
//		subject.setName(newSubjectDTO.getName());
//		subject.setWeekFond(newSubjectDTO.getWeekFond());
		subjectRepository.save(subject);
		
		return subject;
	}
	
	public SubjectEntity changeSubject(String name, SubjectEntity changedSubject) {
		SubjectEntity subject = subjectRepository.findByName(name);
		subject.setId(subject.getId());
		if(changedSubject.getName() != null) {
			subject.setName(changedSubject.getName());
		}
		if(changedSubject.getWeekFond() != null) {
			subject.setWeekFond(changedSubject.getWeekFond());
		}
		return subject;
	}

}
