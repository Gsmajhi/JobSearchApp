package com.jobSerachWebsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobSerachWebsite.Entities.Users;

public interface UserRepository extends JpaRepository<Users,Integer> {
	public Users findUserByEmail(String email);
	public Users findUserByVarificationCode(String varificationCode);
}
