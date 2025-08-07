package com.hexaware.cozyhavenproject.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.cozyhavenproject.entities.BookingPerson;




public interface BookingPersonRepository  extends JpaRepository <BookingPerson, Integer>{
	List<BookingPerson> findByBooking_BookingId(Integer bookingId);
}
