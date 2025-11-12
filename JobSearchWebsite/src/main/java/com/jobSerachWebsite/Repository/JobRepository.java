package com.jobSerachWebsite.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobSerachWebsite.Entities.Jobs;

public interface JobRepository extends JpaRepository<Jobs,String>{
	@Query("SELECT j FROM Jobs j WHERE "+
			"(:Job_Loc IS NULL OR LOWER(j.Job_Loc)= LOWER(:Job_Loc)) AND "
			+"(:Job_Name IS NULL OR LOWER(j.Job_Name)= LOWER(:Job_Name)) AND "
			+"(:Job_Type IS NULL OR LOWER(j.Job_Type)= LOWER(:Job_Type))"
			)
public	List<Jobs> findJobs(@Param("Job_Loc") String location, @Param("Job_Name") String name, @Param("Job_Type") String type);
	public List<Jobs> findJobsByRecruiterId(int id);
	
}
