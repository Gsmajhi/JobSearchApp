package com.jobSerachWebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jobSerachWebsite.Entities.Jobs;
import com.jobSerachWebsite.Entities.Recruiter;
import com.jobSerachWebsite.Repository.JobRepository;
import com.jobSerachWebsite.Repository.RecruiterRepo;
@SpringBootApplication
public class JobSearchWebsiteApplication {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(JobSearchWebsiteApplication.class, args);
		JobRepository jobRepository=context.getBean(JobRepository.class);
		RecruiterRepo recruiterRepo=context.getBean(RecruiterRepo.class);
		Jobs job=new Jobs();
		job.setJob_Name("SDE");
		job.setJob_Type("Full time");
		job.setJob_Loc("Hyderabad");
		job.setJob_Desc("SDE job for Freshers");
		
		
		//Jobs jo=jobRepository.save(job);
		
		
		Jobs job2=new Jobs();
		job2.setJob_Name("SDE");
		job2.setJob_Type("Full time");
		job2.setJob_Loc("Hyderabad");
		job2.setJob_Desc("SDE job for Freshers");
		//Jobs jobb=jobRepository.save(job2);
		
		//List<Jobs> list=jobRepository.findAll();
		
		Recruiter recr=new Recruiter();
		recr.setRecruiter_Name("Java bean");
		recr.setEmail("gs@majhi.com");
		recr.setPassword("889");
		//Recruiter recru=recruiterRepo.save(recr);
		
		System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}

}
