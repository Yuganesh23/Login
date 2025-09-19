package service;

import java.util.List;

import entity.LoginEntity;

public interface LoginService {

 LoginEntity save (LoginEntity le);
	 
	 List <LoginEntity> getAll();
	 
	 LoginEntity getById(Integer id);
	 
	 LoginEntity update(Integer id,LoginEntity ule);
	 
	 void delete (Integer id);
	 

	    // Get user by email (for authentication)
	    LoginEntity getByEmail(String email);
}
