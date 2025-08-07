package com.hexaware.cozyhavenproject.service.impl;
import com.hexaware.cozyhavenproject.entities.Booking;
import com.hexaware.cozyhavenproject.repository.BookingRepository;
import com.hexaware.cozyhavenproject.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService  {

	 @Autowired
	    private BookingRepository bookingRepository;

	    public Booking createBooking(Booking booking) {
	        return bookingRepository.save(booking);
	    }

	    public Booking getBookingById(Integer id) {
	        return bookingRepository.findById(id).orElse(null);
	    }

	    public List<Booking> getAllBookings() {
	        return bookingRepository.findAll();
	    }

	    public Booking updateBooking(Booking booking) {
	        return bookingRepository.save(booking);
	    }

	    public void deleteBooking(Integer id) {
	        bookingRepository.deleteById(id);
	    }
}
