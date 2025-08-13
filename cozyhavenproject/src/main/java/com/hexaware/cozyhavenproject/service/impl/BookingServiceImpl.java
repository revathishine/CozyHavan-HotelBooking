//package com.hexaware.cozyhavenproject.service.impl;
//import com.hexaware.cozyhavenproject.entities.Booking;
//import com.hexaware.cozyhavenproject.repository.BookingRepository;
//import com.hexaware.cozyhavenproject.service.BookingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookingServiceImpl implements BookingService  {
//
//	 @Autowired
//	    private BookingRepository bookingRepository;
//
//	    public Booking createBooking(Booking booking) {
//	        return bookingRepository.save(booking);
//	    }
//
//	    public Booking getBookingById(Integer id) {
//	        return bookingRepository.findById(id).orElse(null);
//	    }
//
//	    public List<Booking> getAllBookings() {
//	        return bookingRepository.findAll();
//	    }
//
//	    public Booking updateBooking(Booking booking) {
//	        return bookingRepository.save(booking);
//	    }
//
//	    public void deleteBooking(Integer id) {
//	        bookingRepository.deleteById(id);
//	    }
//}

package com.hexaware.cozyhavenproject.service.impl;

import com.hexaware.cozyhavenproject.entities.Booking;
import com.hexaware.cozyhavenproject.repository.BookingRepository;
import com.hexaware.cozyhavenproject.service.BookingService;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
	public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
	public Booking getBookingById(Integer id) {
        return bookingRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
	public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
	public Booking updateBooking(Booking booking) {
        Integer id = booking.getBookingId();
        if (id == null || !bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }
        return bookingRepository.save(booking);
    }

    @Override
	public void deleteBooking(Integer id) {
        if (!bookingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking not found with id: " + id);
        }
        bookingRepository.deleteById(id);
    }
}
