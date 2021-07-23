package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.TeacherSubjectClassroom;

public interface TeacherSubjectClassroomDAO {
	
	public TeacherSubjectClassroom assignTeacherSubjectToClassroom(Integer teacherSubjectId, Integer classroomId);

}
