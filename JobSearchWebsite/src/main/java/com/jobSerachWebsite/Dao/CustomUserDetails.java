package com.jobSerachWebsite.Dao;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jobSerachWebsite.Entities.Users;

public class CustomUserDetails implements UserDetails {
	private Users u;
	
	public CustomUserDetails(Users u) {
		super();
		this.u = u;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority simp=new SimpleGrantedAuthority(u.getRole());
		
		return Arrays.asList(simp);
	}

	@Override
	public String getPassword() {
		
		return u.getPassword();
	}

	@Override
	public String getUsername() {
		
		return u.getEmail();
	}

}
