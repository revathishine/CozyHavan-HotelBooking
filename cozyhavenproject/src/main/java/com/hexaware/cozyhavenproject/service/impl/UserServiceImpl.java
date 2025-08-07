package com.hexaware.cozyhavenproject.service.impl;

import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.repository.UserRepository;
import com.hexaware.cozyhavenproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	 @Autowired
	    private UserRepository userRepository;

	    @Override
	    public User createUser(User user) {
	        return userRepository.save(user);
	    }

	    @Override
	    public User getUserById(Integer id) {
	        return userRepository.findById(id).orElse(null);
	    }

	    @Override
	    public List<User> getAllUsers() {
	        return userRepository.findAll();
	    }

	    @Override
	    public User updateUser(User user) {
	        return userRepository.save(user);
	    }

	    @Override
	    public void deleteUser(Integer id) {
	        userRepository.deleteById(id);
	    }
}
