package com.jobSerachWebsite.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.UserRepository;

@Service
public class UsersServiceImpl implements UsersService {
	@Autowired
	EmailServiceImpl emailService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepo;
	@Override
	public Users saveUser(Users user, String url) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		user.setEnabled(false);
		user.setVarificationCode(UUID.randomUUID().toString());
		Users us=userRepo.save(user);
		if(us!=null) {
			emailService.sendEmail(us, url);
		}
		return us;
	}
	@Override
	public boolean varify(String varificationCode) {
		boolean b=false;
		Users user=userRepo.findUserByVarificationCode(varificationCode);
		if(user!=null) {
			user.setVarificationCode(null);
			user.setEnabled(true);
			userRepo.save(user);
			b=true;
		}
		return b;
		
	}
	
}
