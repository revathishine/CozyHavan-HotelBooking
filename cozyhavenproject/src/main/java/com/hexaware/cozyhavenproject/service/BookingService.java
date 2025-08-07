package com.hexaware.cozyhavenproject.service;
import com.hexaware.cozyhavenproject.entities.Booking;
import java.util.List;

public interface BookingService {

	 Booking createBooking(Booking booking);
	    Booking getBookingById(Integer id);
	    List<Booking> getAllBookings();
	    Booking updateBooking(Booking booking);
	    void deleteBooking(Integer id);
}
