package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.StudentEntity;

public interface StudentDAO {
	
	public StudentEntity assignToClassroom(Integer studentId, Integer classroomId);
	public StudentEntity assignToParent(Integer studentId, Integer parentId);

}
