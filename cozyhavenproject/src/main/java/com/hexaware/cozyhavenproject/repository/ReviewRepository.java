package com.hexaware.cozyhavenproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyhavenproject.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
 
	List<Review> findByHotel_HotelId(Integer hotelId);
}
