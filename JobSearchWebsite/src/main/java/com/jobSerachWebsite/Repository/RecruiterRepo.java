package com.jobSerachWebsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobSerachWebsite.Entities.Recruiter;

public interface RecruiterRepo extends JpaRepository<Recruiter,Integer>{
	
}
