package com.jobSerachWebsite.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jobSerachWebsite.Entities.Application;
import com.jobSerachWebsite.Entities.Jobs;
import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.ApplicationRepository;
import com.jobSerachWebsite.Repository.JobRepository;
import com.jobSerachWebsite.Repository.UserRepository;

@Controller
@RequestMapping("/user/**")
public class UserController {

	@Autowired
	JobRepository jobRepo;

	@Autowired
	ApplicationRepository appRepo;
	@Autowired
	UserRepository userRepo;

	@RequestMapping("/home")
	public String home() {
		return "redirect:/jobs";
	}

	@RequestMapping("/Applyjob/{id}")
	public String applyJob(@PathVariable String id, Principal p, Model m) {
		
		System.out.println("entered applyjob method");
		if (p == null) {
			m.addAttribute("msg", "you are not logedin");
			return "redirect:/userlogin";
			
		}
		String email = p.getName();
		Users user = userRepo.findUserByEmail(email);
		System.out.println("inside jobapply method"+user);
		
		
		m.addAttribute("user", user);
		Jobs job = jobRepo.findById(id).orElse(null);
		System.out.println("inside applyjob method"+job);
		

		if (job != null) {
			m.addAttribute("job", job);
			m.addAttribute("application", new Application());
			System.out.println("job set");
			return "userDetails";
		} else {
			System.out.println("job is null");
			return "jobnotound";
		}

	}

	private static final String UPLOAD_DIR = "tmp/pdfs/";

	@RequestMapping(path = "/submitApplication", method = RequestMethod.POST)
	public String submitApplication(@RequestParam("resume") MultipartFile file, @RequestParam("name") String name,
			@RequestParam("jobid") String jobid, @RequestParam("userid") int userid, Model m) throws IOException {

		Application application = new Application();
		Jobs job = jobRepo.findById(jobid).orElse(null);

		if (!file.isEmpty()) {
			Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			application.setJob(job);
			application.setResume("/pdfs/" + file.getOriginalFilename());
			application.setUserId(userid);
			application.setName(name);
			Application ap = appRepo.save(application);
			if (ap != null) {
				m.addAttribute("msg", "Job Applied Successfully ");
				return "jobApplied";
			} else {
				m.addAttribute("msg", "Something went wrong");
				return "jobApplied";
			}

		} else {
			m.addAttribute("msg", "Something went wrong");
			return "jobApplied";
		}

	}
}



