package com.jobSerachWebsite.Config;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	
	@Autowired
	private UserRepository userRepo;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String email=request.getParameter("username");
		Users user=userRepo.findUserByEmail(email);
		if(user!=null) {
			if(user.isEnabled()) {
				
			}else {
				exception=new LockedException("Accound not verified verify your Account");
			}
			
		}else {
			exception=new LockedException("Email not registered");
		}
		String errorMessage=exception.getMessage();
		
		super.setDefaultFailureUrl("/userlogin?error="+errorMessage);
		super.onAuthenticationFailure(request, response, exception);
	}
	
}
