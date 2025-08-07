package com.hexaware.cozyhavenproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyhavenproject.entities.RevokedToken;

public interface RevokedTokenRepository  extends JpaRepository<RevokedToken, Integer>{

	RevokedToken findByToken(String token);
}
