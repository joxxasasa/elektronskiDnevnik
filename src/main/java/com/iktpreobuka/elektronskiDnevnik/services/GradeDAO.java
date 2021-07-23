package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.GradeEntityDTO;

public interface GradeDAO {
	
	public GradeEntity gradingStudent(Integer studentId, Integer teacherSubjectClassroomId, GradeEntityDTO newGradeDTO);

}