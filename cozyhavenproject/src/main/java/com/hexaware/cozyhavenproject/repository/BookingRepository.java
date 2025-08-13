package com.hexaware.cozyhavenproject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.cozyhavenproject.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
	List<Booking> findByUser_UserId(Integer userId);
	@Query("""
	        select b.room.roomId 
	        from Booking b 
	        where b.room.hotel.hotelId = :hotelId 
	          and b.status = 'CONFIRMED'
	          and (b.checkInDate < :endDate and b.checkOutDate > :startDate)
	    """)
	 List<Integer> findBookedRoomIdsByHotelAndDateOverlap(
	            @Param("hotelId") Integer hotelId,
	            @Param("startDate") LocalDate startDate,
	            @Param("endDate") LocalDate endDate);
	@Query("""
	        select (count(b) > 0) 
	        from Booking b 
	        where b.room.roomId = :roomId 
	          and b.status = 'CONFIRMED'
	          and (b.checkInDate < :endDate and b.checkOutDate > :startDate)
	    """)
	boolean existsActiveOverlap(
            @Param("roomId") Integer roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
