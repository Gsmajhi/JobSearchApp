package com.jobSerachWebsite.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jobSerachWebsite.Entities.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage,Integer>{
	
	@Query("SELECT u.imagePath FROM UserImage u WHERE u.userId= :userId")
	String findImagePathByUserId(@Param("userId") int userId);

}
