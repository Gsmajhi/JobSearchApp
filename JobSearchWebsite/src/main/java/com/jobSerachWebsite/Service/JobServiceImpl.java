package com.jobSerachWebsite.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobSerachWebsite.Entities.Jobs;
import com.jobSerachWebsite.Repository.JobRepository;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	JobRepository jobrepo;
		public List<Jobs> findJobs(String jobloc,String jobname,String jobtype){
			return jobrepo.findJobs(jobloc,jobname,jobtype);
		}
		public Jobs getJobById(String id) {
			 Optional<Jobs> j=jobrepo.findById(id);
			 
			 
			 return j.get();
		}
		public List<Jobs> findJobByRecrId(int Recruiterid){
			List<Jobs> job=jobrepo.findJobsByRecruiterId(Recruiterid);
			return job;
			
		}

}
