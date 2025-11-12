package com.jobSerachWebsite.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Application {
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int ApplicationId;
		private int userId;
		private String name;
		
		@ManyToOne
		
		private Jobs job;
		private String resume;
		public int getApplicationId() {
			return ApplicationId;
		}

		public void setApplicationId(int applicationId) {
			ApplicationId = applicationId;
		}

		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		
		

		

		public Jobs getJob() {
			return job;
		}

		public void setJob(Jobs job) {
			this.job = job;
		}

		
		public String getResume() {
			return resume;
		}

		public void setResume(String resume) {
			this.resume = resume;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		
}
