package com.iktpreobuka.elektronskiDnevnik.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.elektronskiDnevnik.entities.GradeEntity;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	private GradeDAO gradeDAO;
	
	@Override
	public void sendGradeInfo(GradeEntity grade) throws Exception {
		
		MimeMessage mail = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, true);
		helper.setTo(grade.getStudent().getParent().getEmail());
		helper.setSubject("Information about new grade!");
		String text = "<html>"
				+"<head><style>table, th, td {border: 1px solid black;} th { text-align: center;} </style>"
				+ "<body><H2>NEW GRADE</h2>"
				+ "<table style='width: 70%'>"
				+ "<tr><th>Date</th>"
			    + "		<th>Teacher</th>"
			    + "		<th>Subject</th>"
			    + "		<th>Value</th></tr>"
			    + "<tr><td style=\"text-align:center\">" + grade.getDate() + "</td>"
			    + "    <td style=\"text-align:center\">" + grade.getTeacherSubjectClassroom().getTeacherSubject().getTeacher().getLastname()
			    + " " + grade.getTeacherSubjectClassroom().getTeacherSubject().getTeacher().getName() + "</td>"
			    + "    <td style=\"text-align:center\">" + grade.getTeacherSubjectClassroom().getTeacherSubject().getSubject().getName() + "</td>"
			    + "    <td style=\"text-align:center\">" + grade.getValue() + "</td></tr>"
			    + "</table>"
			    + "</body>"
			    + "</html>";
		helper.setText(text, true);
		emailSender.send(mail);
	}

}
