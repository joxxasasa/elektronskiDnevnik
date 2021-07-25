package com.iktpreobuka.elektronskiDnevnik.services;

import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;

public interface EmailService {

	public void sendGradeInfo(GradeEntity grade) throws Exception;

}
