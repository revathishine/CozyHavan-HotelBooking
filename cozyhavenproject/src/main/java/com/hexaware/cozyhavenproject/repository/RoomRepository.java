package com.hexaware.cozyhavenproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyhavenproject.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

	List<Room> findByHotel_HotelId(Integer hotelId);
    List<Room> findByAvailabilityStatusTrue();
}
