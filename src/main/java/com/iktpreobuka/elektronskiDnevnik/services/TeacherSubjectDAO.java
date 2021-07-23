package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubject;

public interface TeacherSubjectDAO {
	
	public TeacherSubject assignSubjectTeacher(Integer teacherId, Integer subjectId);

}
