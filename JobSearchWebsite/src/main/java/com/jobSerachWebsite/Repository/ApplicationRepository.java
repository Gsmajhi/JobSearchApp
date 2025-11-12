package com.jobSerachWebsite.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobSerachWebsite.Entities.Application;
import com.jobSerachWebsite.Entities.Jobs;

public interface ApplicationRepository extends JpaRepository<Application,Integer>{
	@Query("SELECT a FROM Application a WHERE a.job.Job_Id = :jobId")
	List<Application> findApplicationsByJobId(@Param("jobId") String jobId);
	
}
