package com.jobSerachWebsite.Service;

import com.jobSerachWebsite.Entities.Users;

public interface EmailService {
	public void sendEmail(Users user,String url);
	
}
