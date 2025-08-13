//package com.hexaware.cozyhavenproject.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.hexaware.cozyhavenproject.entities.User;
//import com.hexaware.cozyhavenproject.repository.UserRepository;
//import com.hexaware.cozyhavenproject.service.UserService;
//
//@Service
//public class UserServiceImpl implements UserService {
//	 @Autowired
//	    private UserRepository userRepository;
//
//	    @Override
//	    public User createUser(User user) {
//	        return userRepository.save(user);
//	    }
//
//	    @Override
//	    public User getUserById(Integer id) {
//	        return userRepository.findById(id).orElse(null);
//	    }
//
//	    @Override
//	    public List<User> getAllUsers() {
//	        return userRepository.findAll();
//	    }
//
//	    @Override
//	    public User updateUser(User user) {
//	        return userRepository.save(user);
//	    }
//
//	    @Override
//	    public void deleteUser(Integer id) {
//	        userRepository.deleteById(id);
//	    }
//	    
//
//}

package com.hexaware.cozyhavenproject.service.impl;

import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.repository.UserRepository;
import com.hexaware.cozyhavenproject.service.UserService;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        Integer id = user.getUserId();
        if (id == null || !userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
