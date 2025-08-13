//package com.hexaware.cozyhavenproject.service.impl;
//import com.hexaware.cozyhavenproject.entities.BookingPerson;
//import com.hexaware.cozyhavenproject.repository.BookingPersonRepository;
//import com.hexaware.cozyhavenproject.service.BookingPersonService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class BookingPersonServiceImpl implements BookingPersonService{
//
//	 @Autowired
//	    private BookingPersonRepository bookingPersonRepository;
//
//	    public BookingPerson createPerson(BookingPerson person) {
//	        return bookingPersonRepository.save(person);
//	    }
//
//	    public BookingPerson getPersonById(Integer id) {
//	        return bookingPersonRepository.findById(id).orElse(null);
//	    }
//
//	    public List<BookingPerson> getAllPersons() {
//	        return bookingPersonRepository.findAll();
//	    }
//
//	    public BookingPerson updatePerson(BookingPerson person) {
//	        return bookingPersonRepository.save(person);
//	    }
//
//	    public void deletePerson(Integer id) {
//	        bookingPersonRepository.deleteById(id);
//	    }
//}
package com.hexaware.cozyhavenproject.service.impl;

import com.hexaware.cozyhavenproject.entities.BookingPerson;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.BookingPersonRepository;
import com.hexaware.cozyhavenproject.service.BookingPersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingPersonServiceImpl implements BookingPersonService {

    @Autowired
    private BookingPersonRepository bookingPersonRepository;

    @Override
    public BookingPerson createPerson(BookingPerson person) {
        return bookingPersonRepository.save(person);
    }

    @Override
    public BookingPerson getPersonById(Integer id) {
        return bookingPersonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking person not found with id: " + id));
    }

    @Override
    public List<BookingPerson> getAllPersons() {
        return bookingPersonRepository.findAll();
    }

    @Override
    public BookingPerson updatePerson(BookingPerson person) {
        Integer id = person.getPersonId();
        if (id == null || !bookingPersonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking person not found with id: " + id);
        }
        return bookingPersonRepository.save(person);
    }

    @Override
    public void deletePerson(Integer id) {
        if (!bookingPersonRepository.existsById(id)) {
            throw new ResourceNotFoundException("Booking person not found with id: " + id);
        }
        bookingPersonRepository.deleteById(id);
    }
}
