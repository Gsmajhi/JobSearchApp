package com.jobSerachWebsite.Service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.UserRepository;
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	@Autowired
	UserRepository userRepo;
	

	

	
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oauthUser= super.loadUser(userRequest);
		

		String email=oauthUser.getAttribute("email");
		String name=oauthUser.getAttribute("name");
		if(email==null && name==null) {
			System.out.println("null values");
		}
		
		Users user=userRepo.findUserByEmail(email);
		
		if(user==null) {
			user=new Users();
			user.setEmail(email);
			user.setName(name);
			user.setRole("ROLE_USER");
			user.setProvider(userRequest.getClientRegistration().getRegistrationId());
			Users u=userRepo.save(user);
			
		}
		String grantedRole=user.getRole().toUpperCase();
		
		SimpleGrantedAuthority authority=new SimpleGrantedAuthority(grantedRole);
		
		String userNameAttributeName=userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		return new DefaultOAuth2User(
			Collections.singletonList(authority),oauthUser.getAttributes(),userNameAttributeName
		);
	}
	

}
