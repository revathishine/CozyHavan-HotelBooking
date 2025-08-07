package com.hexaware.cozyhavenproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyhavenproject.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	 List<Hotel> findByLocation(String location);
}
