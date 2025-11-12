package com.jobSerachWebsite.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user=userRepo.findUserByEmail(username);
		if(user==null) {
			throw new UsernameNotFoundException("User Name Not Found");
		}
		else {
			return new CustomUserDetails(user);
		}
	}

}
