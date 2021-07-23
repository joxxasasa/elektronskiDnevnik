package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.ClassroomEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.ClassroomDTO;

public interface ClassroomDAO {
	
	public ClassroomEntity createNewClassroom(ClassroomDTO newClassroomDTO);
	public ClassroomEntity changeClassroom(Integer id, ClassroomDTO changedClassroomDTO);

}
