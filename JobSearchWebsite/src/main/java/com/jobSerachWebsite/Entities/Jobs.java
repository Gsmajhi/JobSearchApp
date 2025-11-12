package com.jobSerachWebsite.Entities;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Jobs {
		@Id
		@GeneratedValue(generator="Custom-id")
		@GenericGenerator(name="Custom-id",strategy="com.jobSerachWebsite.Entities.CustomIdGenerator")
		private String Job_Id;
		private String Job_Name;
		private String Job_Type;
		private String Job_Desc;
		private String Job_Loc;
		
		@ManyToOne
		@JoinColumn(name="Recruiter_id")
		private  Recruiter recruiter;
		
		@OneToMany(mappedBy="job")
		private List<Application> application;
		
		
		
		
		public String getJob_Id() {
			return Job_Id;
		}
		public void setJob_Id(String job_Id) {
			Job_Id = job_Id;
		}
		public String getJob_Name() {
			return Job_Name;
		}
		public void setJob_Name(String job_Name) {
			Job_Name = job_Name;
		}
		public String getJob_Type() {
			return Job_Type;
		}
		public void setJob_Type(String job_Type) {
			Job_Type = job_Type;
		}
		public String getJob_Desc() {
			return Job_Desc;
		}
		public void setJob_Desc(String job_Desc) {
			Job_Desc = job_Desc;
		}
		public String getJob_Loc() {
			return Job_Loc;
		}
		public void setJob_Loc(String job_Loc) {
			Job_Loc = job_Loc;
		}
		public Recruiter getRecruiter() {
			return recruiter;
		}
		public void setRecruiter(Recruiter recruiter) {
			this.recruiter = recruiter;
		}
		public List<Application> getApplication() {
			return application;
		}
		public void setApplication(List<Application> application) {
			this.application = application;
		}
		
		
		
}
