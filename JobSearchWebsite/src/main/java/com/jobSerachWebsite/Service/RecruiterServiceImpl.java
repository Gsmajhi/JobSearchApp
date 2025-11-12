package com.jobSerachWebsite.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jobSerachWebsite.Entities.Recruiter;
import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.RecruiterRepo;
import com.jobSerachWebsite.Repository.UserRepository;


@Service
public class RecruiterServiceImpl implements RecruiterService {
	
	@Autowired
	EmailServiceImpl emailService;
	@Autowired
	UserRepository userRepo;
	@Autowired
	RecruiterRepo recruiterRepo;	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@Override
	public Recruiter saveRecruiter(Recruiter recruiter,String url) {
		recruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));
		
		Recruiter recr=recruiterRepo.save(recruiter);
		Users user=new Users();
		user.setEmail(recr.getEmail());
		user.setPassword(recr.getPassword());
		user.setName(recr.getRecruiter_Name());
		user.setEnabled(false);
		user.setRole("ROLE_RECRUITER");
		user.setVarificationCode(UUID.randomUUID().toString());
		Users us=userRepo.save(user);
		if(us!=null) {
			emailService.sendEmail(us, url);
		}
		return recr;
	}

}
