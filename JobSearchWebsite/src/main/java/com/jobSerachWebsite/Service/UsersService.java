package com.jobSerachWebsite.Service;

import com.jobSerachWebsite.Entities.Users;

public interface UsersService {
	public Users saveUser(Users user,String url);
	public boolean varify(String varificationCode);
}
