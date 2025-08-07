package com.hexaware.cozyhavenproject.service;
import java.util.List;

import com.hexaware.cozyhavenproject.entities.User;

public interface UserService {
 
	 User createUser(User user);
	    User getUserById(Integer id);
	    List<User> getAllUsers();
	    User updateUser(User user);
	    void deleteUser(Integer id);
}
