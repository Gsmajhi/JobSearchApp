package com.jobSerachWebsite.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.UserRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	UserRepository userRepo;
	@Override
	public void sendEmail(Users user, String url) {
		
		String from="gsmajhi36@gmail.com";
		String to=user.getEmail();
		String subject="Account Varification";
		String content="Dear [[name]],<br>" +
                "Please click the link below to verify your account:<br>" +
                "<h3><a href=\"[[URL]]\" target=\"_blank\">VERIFY</a></h3>" +
                "Thank you,<br>" +
                "JobSearch";
		try {
			MimeMessage message=mailSender.createMimeMessage();
			MimeMessageHelper helper=new MimeMessageHelper(message);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			content=content.replace("[[name]]",user.getName());
			String siteUrl=url+"/verify?code="+user.getVarificationCode();
			content=content.replace("[[URL]]",siteUrl);
			helper.setText(content,true);
			mailSender.send(message);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
