package com.jobSerachWebsite.Config;

import java.io.IOException;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest=new HttpSessionRequestCache().getRequest(request,response);
		
		if(savedRequest!=null) {
			String targetUrl=savedRequest.getRedirectUrl();
			response.sendRedirect(targetUrl);
		}else {
		Set<String> roles=AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if(roles.contains("ROLE_RECRUITER")) {
			response.sendRedirect("/recruiter/profile");
		}else {
			response.sendRedirect("/jobs");
		}
	}
	}

}
