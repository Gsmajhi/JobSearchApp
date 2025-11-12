package com.jobSerachWebsite.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jobSerachWebsite.Entities.Application;
import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.ApplicationRepository;
import com.jobSerachWebsite.Repository.UserRepository;

@Controller
@RequestMapping("/recruiter/**")
public class RecruiterController {
	
	@Autowired
	UserRepository userRepo;		
	
	@Autowired
	ApplicationRepository appRepo;
	@ModelAttribute
	public void showText(Principal p,Model m) {
		if(p!=null) {
			String name=p.getName();
			Users user=userRepo.findUserByEmail(name);
			m.addAttribute("user",user);
		}
	}
	
	@RequestMapping("/profile")
	public String profile() {
		return "profile";
	}
	
	@RequestMapping("/applicatiosbyjobId/{id}")
	public String showApplicationsByJobId(@PathVariable String id,Model m) {
		List<Application> applications=appRepo.findApplicationsByJobId(id);
		if(applications!=null  && !applications.isEmpty()) {
			m.addAttribute("applications",applications);
			
		}else {
			m.addAttribute("msg","No applications yet");
			
		}return"showApplications";
		
		
	}
	@RequestMapping("/showResume/{id}")
	public String showResume(@PathVariable int id,Model m) {
		Application app=appRepo.getById(id);
		System.out.println(app.getResume());
		m.addAttribute("application",app);
		m.addAttribute("path",app.getResume());
		
		return"showResume";
	}

}
