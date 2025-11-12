package com.jobSerachWebsite.Entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity

public class Recruiter {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String Recruiter_Name;
	
	@OneToMany(mappedBy="recruiter")
	private List<Jobs> jobs;
	private String email;
	private String password;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRecruiter_Name() {
		return Recruiter_Name;
	}
	public void setRecruiter_Name(String recruiter_Name) {
		Recruiter_Name = recruiter_Name;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Jobs> getJobs() {
		return jobs;
	}
	public void setJobs(List<Jobs> jobs) {
		this.jobs = jobs;
	}
	

}
