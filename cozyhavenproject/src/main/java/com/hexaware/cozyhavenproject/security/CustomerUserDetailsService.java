package com.hexaware.cozyhavenproject.security;

import com.hexaware.cozyhavenproject.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.cozyhavenproject.repository.UserRepository;

@Service 
public class CustomerUserDetailsService implements UserDetailsService {

	 @Autowired
	    private UserRepository userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	        User u = userRepository.findByEmail(email);
	        if (u == null) {
	            throw new UsernameNotFoundException("User not found with email: " + email);
	        }
	        return new CustomerUserDetails(u);
	    }
}
