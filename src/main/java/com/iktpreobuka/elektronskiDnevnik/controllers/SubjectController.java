package com.iktpreobuka.elektronskiDnevnik.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.elektronskiDnevnik.controllers.util.RESTError;
import com.iktpreobuka.elektronskiDnevnik.entities.SubjectEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.SubjectEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.SubjectRepository;
import com.iktpreobuka.elektronskiDnevnik.services.SubjectDAOImpl;

@RestController
@RequestMapping(value = "/subjects")
public class SubjectController {

	@Autowired
	private SubjectDAOImpl subjectDAOImpl;

	@Autowired
	private SubjectRepository subjectRepository;

	@PostMapping("/createSubject")
	public ResponseEntity<?> createSubject(@Valid @RequestBody SubjectEntityDTO newSubjectDTO) {

		try {
			if (newSubjectDTO != null) {
				SubjectEntity subject = new SubjectEntity();
				subject.setName(newSubjectDTO.getName());
				subject.setWeekFond(newSubjectDTO.getWeekFond());
				subjectDAOImpl.createNewSubject(subject);
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject can not be crated"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findByName/{name}")
	public ResponseEntity<?> findSubjectByName(@PathVariable String name) {
		try {
			if (name != null) {
				SubjectEntity subject = subjectRepository.findByName(name);
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/findAll")
	public ResponseEntity<?> findAllSubjects() {
		try {
			List<SubjectEntity> subjects = (List<SubjectEntity>) subjectRepository.findAll();
			if (!subjects.isEmpty()) {
				return new ResponseEntity<List<SubjectEntity>>(subjects, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subjects can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/changeSubject/{name}")
	public ResponseEntity<?> changeSubject(@PathVariable String name, @RequestBody SubjectEntity changedSubject) {
		try {

			if (subjectRepository.existsById(subjectRepository.findByName(name).getId())) {
				SubjectEntity subject = subjectDAOImpl.changeSubject(name, changedSubject);
				subjectRepository.save(subject);
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subjects can not be found"), HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deleteSubject/{name}")
	public ResponseEntity<?> deleteSubject(@PathVariable String name) {
		try {
			if (subjectRepository.existsById(subjectRepository.findByName(name).getId())) {
				SubjectEntity subject = subjectRepository.findByName(name);
				subjectRepository.delete(subject);
				return new ResponseEntity<SubjectEntity>(subject, HttpStatus.OK);
			}
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not found!"), HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(
					new RESTError(2, "While requesting user from DB error ocured. Error message " + e.getMessage()
							+ e.getStackTrace() + ".\n Stack trace:	"),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
