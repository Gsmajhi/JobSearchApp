package com.jobSerachWebsite.Service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.jobSerachWebsite.Entities.Jobs;

@Service
public interface JobService {
	
		public List<Jobs> findJobs(String jobloc,String jobname,String jobtype);
		public Jobs getJobById(String id);
		
		public List<Jobs> findJobByRecrId(int Recruiterid);
}
