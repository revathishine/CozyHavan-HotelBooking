package com.hexaware.cozyhavenproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyhavenproject.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	 User findByEmail(String email);

}
