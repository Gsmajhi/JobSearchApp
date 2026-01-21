package com.jobSerachWebsite.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jobSerachWebsite.Entities.Jobs;
import com.jobSerachWebsite.Entities.Recruiter;
import com.jobSerachWebsite.Entities.UserImage;
import com.jobSerachWebsite.Entities.Users;
import com.jobSerachWebsite.Repository.JobRepository;
import com.jobSerachWebsite.Repository.RecruiterRepo;
import com.jobSerachWebsite.Repository.UserImageRepository;
import com.jobSerachWebsite.Repository.UserRepository;
import com.jobSerachWebsite.Service.JobService;
import com.jobSerachWebsite.Service.JobServiceImpl;
import com.jobSerachWebsite.Service.RecruiterServiceImpl;
import com.jobSerachWebsite.Service.UsersServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
	@Autowired
	JobServiceImpl jobService;

	@Autowired
	RecruiterServiceImpl recService;

	@Autowired
	UsersServiceImpl userServ;
	@Autowired
	UserRepository userRepo;
	@Autowired
	RecruiterRepo recruiterRepo;
	@Autowired
	JobRepository jobRepo;

	@Autowired
	UserImageRepository userImageRepo;

	private static final String IMAGE_PATH = "/tmp/images/";

	@RequestMapping({"/","/jobs"})
	public String home(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Users user = userRepo.findUserByEmail(email);
			System.out.println("email"+email);
			if (user != null) {
				String imagePath = userImageRepo.findImagePathByUserId(user.getId());
				// System.out.println(user.getId()+imagePath);
				m.addAttribute("imagepath", imagePath);
				m.addAttribute("user", user);

				String role = user.getRole();

				if (role.equals("ROLE_RECRUITER")) {
					return "redirect:/recruiter/profile";
				}
			}

		}

		List<Jobs> list = jobRepo.findAll();

		m.addAttribute("jobs", list);

		return "jobs";
	}

	@RequestMapping(path = "/recruRegform")
	public String showRecruiterReg(Model m) {
		Recruiter recruiter = new Recruiter();
		m.addAttribute("recruiter", recruiter);
		return "recruiter_registration";
	}

	@RequestMapping(path = "/recruiter_registration", method = RequestMethod.POST)
	public String recruiterregistration(@ModelAttribute Recruiter recruiter, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		url = url.replace(request.getServletPath(), "");
		Recruiter recr = recService.saveRecruiter(recruiter, url);
		if (recr != null) {
			redirectAttrs.addFlashAttribute("msg", "Registration Successful");
		}
		return "redirect:/recruRegform";
	}

	@RequestMapping(path = "/showjobform")
	public String showJobForm(Model m, Principal p) {
		String email = p.getName();
		Users user = userRepo.findUserByEmail(email);
		m.addAttribute("user", user);
		m.addAttribute("jobs", new Jobs());

		return "postjob";
	}

	
	@RequestMapping(path = "/postjob", method = RequestMethod.POST)
public String postJob(@ModelAttribute Jobs jobs,
                      Principal p,
                      RedirectAttributes redirectAttrs) {

    String email = p.getName();
    Users user = userRepo.findUserByEmail(email);

    Recruiter recruiter = recruiterRepo.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Recruiter not found for user: " + email));

    jobs.setRecruiter(recruiter);
    jobRepo.save(jobs);

    redirectAttrs.addFlashAttribute("msg", "Job Posted");
    return "redirect:/showjobform";
}


	@RequestMapping(path = "/userRegform")
	public String showUserRegForm(Model m) {
		m.addAttribute("users", new Users());
		return "user_registration";
	}

	@RequestMapping(path = "/user_registration", method = RequestMethod.POST)
	public String userRegistration(@ModelAttribute Users users, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		url = url.replace(request.getServletPath(), "");
		Users us = userServ.saveUser(users, url);

		if (us != null) {
			redirectAttrs.addFlashAttribute("msg", "Registration done");
		} else {
			redirectAttrs.addFlashAttribute("msg", "something went wrong");
		}
		return "redirect:/userRegform";
	}

	@RequestMapping(path = "/job_details/{id}")
	public String showJob(@PathVariable("id") String id, Model m, Principal p) {
		Users user = null;
		if (p != null) {
			String email = p.getName();
			user = userRepo.findUserByEmail(email);
			Jobs job = jobRepo.findById(id).orElse(null);
			if (job != null) {
				m.addAttribute("user", user);
				m.addAttribute("job", job);
				return "job_details";
			} else {
				
				
				m.addAttribute("user", user);
				m.addAttribute("msg", "Job Details Nof found !");
				return "jobnotfound";
			}

		} else {
			return "redirect:/userlogin";
		}

	}

	@RequestMapping(path = "/registration")
	public String registration() {
		return "registration";
	}

	@RequestMapping(path = "/userlogin")
	public String useLogin() {
		return "userlogin";
	}

	@RequestMapping("/search")
	public String findJobs(@RequestParam(required = false) String location,
	                       @RequestParam(required = false) String jobname, 
	                       @RequestParam(required = false) String jobtype, 
	                       Model m, // Keep the Model object
	                       Principal p // Add Principal here to maintain user data
	                       ) {
	    
	    // Use the Principal 'p' here to get user details and populate the model
	    if (p != null) {
	        String email = p.getName();
	        Users user = userRepo.findUserByEmail(email);
	        if (user != null) {
	            String imagePath = userImageRepo.findImagePathByUserId(user.getId());
	            m.addAttribute("imagepath", imagePath);
	            m.addAttribute("user", user);

	            String role = user.getRole();
	            if (role.equals("ROLE_RECRUITER")) {
	                // If the logic requires redirecting for a role, use FlashAttributes (see Solution 2)
	                // For a standard search view, proceed as normal
	            }
	        }
	    }

	    List<Jobs> jobs = jobService.findJobs(location, jobname, jobtype);
	    m.addAttribute("jobs", jobs);
	    
	    // Return the view name directly, without "redirect:"
	    return "jobs"; 
	}


	@RequestMapping("/showJobs")
	public String updateJobs(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			Users user = userRepo.findUserByEmail(email);
			List<Jobs> jobs = jobService.findJobByRecrId(user.getId());
			m.addAttribute("jobs", jobs);
			m.addAttribute("user", user);
			return "showJobs";
		} else {
			return "redirect:/userlogin";
		}

	}

	@RequestMapping("/editjobs/{id}")
	public String editJobs(Model m, @PathVariable String id) {
		Jobs job = jobService.getJobById(id);
		m.addAttribute("job", job);
		return "editJobs";
	}

	@RequestMapping("/update")
	public String updateJob(@ModelAttribute Jobs job, RedirectAttributes redirectAttri) {
		System.out.println(job.getJob_Name());
		System.out.println(job.getJob_Desc());
		System.out.println(job.getJob_Loc());
		System.out.println(job.getJob_Type());
		System.out.println(job.getJob_Id());
		Jobs j = jobRepo.save(job);
		redirectAttri.addFlashAttribute("msg", "Job Updated");
		return "redirect:/showJobs";

	}

	@RequestMapping("/delete/{id}")

	public String deleteJob(@PathVariable String id, RedirectAttributes redirectAttributes) {
		jobRepo.deleteById(id);
		redirectAttributes.addFlashAttribute("msg", "Job Deleted");
		return "redirect:/showJobs";
	}

	@RequestMapping("/verify")
	public String verify(@Param("code") String code, Model m) {
		boolean b = userServ.varify(code);
		if (b) {
			m.addAttribute("msg", "Accound Verified");
		} else {
			m.addAttribute("msg", "Code Expired or Already verified");

		}
		return "success";
	}

	@RequestMapping("/userprofile/{id}")
	public String userProfile(Principal p, Model m) {
		Users user = null;
		if (p != null) {
			String email = p.getName();
			user = userRepo.findUserByEmail(email);

		}
		m.addAttribute("user", user);
		return "userprofile";
	}

	@RequestMapping(path = "/imageform")
	public String imageform(Principal p, Model m) {

		if (p != null) {
			String email = p.getName();
			Users user = userRepo.findUserByEmail(email);
			m.addAttribute("user", user);
		}

		return "imageupload";
	}

	@RequestMapping(path = "/uploadImage/{id}", method = RequestMethod.POST)
	public String uploadImage(@RequestParam MultipartFile file, @PathVariable int id, Model m) throws IOException {
		UserImage userimage = new UserImage();
		if (file != null) {
			Files.createDirectories(Paths.get(IMAGE_PATH));

			Path path = Paths.get(IMAGE_PATH + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		}

		userimage.setUserId(id);
		userimage.setImagePath("/images/" + file.getOriginalFilename());
		System.out.println(id);
		System.out.print(file.getOriginalFilename());
		UserImage ui = userImageRepo.save(userimage);
		if (ui != null) {
			m.addAttribute("msg", "image uploaded");
			return "redirect:/userprofile/id";
		} else {
			m.addAttribute("msg", "Something went wrong");
			return "redirect:/userprofile/id";
		}

	}

}







