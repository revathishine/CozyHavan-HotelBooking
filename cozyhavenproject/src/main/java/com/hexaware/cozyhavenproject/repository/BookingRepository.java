package com.hexaware.cozyhavenproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyhavenproject.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByUser_UserId(Integer userId);
}
