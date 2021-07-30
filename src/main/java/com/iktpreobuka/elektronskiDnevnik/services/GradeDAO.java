package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.GradeEntityDTO;
import com.iktpreobuka.elektronskiDnevnik.entities.dto.GradeReportDTO;

public interface GradeDAO {
	
	public GradeEntity gradingStudent(Integer studentId, Integer teacherSubjectClassroomId, GradeEntityDTO newGradeDTO);
	public GradeEntity deleteGrade(Integer gradeId);
	public GradeEntity updateGrade(Integer gradeId, GradeEntityDTO changedGradeDTO);
	public GradeReportDTO generateGradeReport(Integer studentId);

}
