package com.iktpreobuka.elektronskiDnevnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.ClassroomDTO;
import com.iktpreobuka.elektronskiDnevnik.repositories.ClassroomRepository;

@Service
public class ClassroomDAOImpl implements ClassroomDAO{
	
	@Autowired
	private ClassroomRepository classroomRepository;
	
	public ClassroomEntity createNewClassroom(ClassroomDTO newClassroomDTO) {

		ClassroomEntity classroom = new ClassroomEntity();
		classroom.setClassroomNumber(newClassroomDTO.getClassroomNumber());
		classroom.setClassroomMark(newClassroomDTO.getClassroomMark());
		classroomRepository.save(classroom);
		
		return classroom;
	}
	
	public ClassroomEntity changeClassroom(Integer id, ClassroomDTO changedClassroomDTO) {
		ClassroomEntity classroom = classroomRepository.findById(id).get();
		if(changedClassroomDTO.getClassroomNumber() != null)
		classroom.setClassroomNumber(changedClassroomDTO.getClassroomNumber());
		if(changedClassroomDTO.getClassroomMark() != null)
		classroom.setClassroomMark(changedClassroomDTO.getClassroomMark());
		classroomRepository.save(classroom);
		
		return classroom;
	}

}
