//package com.hexaware.cozyhavenproject.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hexaware.cozyhavenproject.dto.BookingPersonDto;
//import com.hexaware.cozyhavenproject.entities.BookingPerson;
//import com.hexaware.cozyhavenproject.service.BookingPersonService;
//
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/booking-persons")
//public class BookingPersonController {
//
//	
//	@Autowired
//    private BookingPersonService bookingPersonService;
//
//    @PostMapping
//    public BookingPerson createPerson(@Valid @RequestBody BookingPersonDto personDto) {
//    	 log.info("POST /api/booking-persons - create booking person for booking {}", personDto.getBookingId());
//        BookingPerson person = new BookingPerson();
//        person.setAge(personDto.getAge());
//        person.setIsChild(personDto.getIsChild());
//        return bookingPersonService.createPerson(person);
//    }
//
//    @GetMapping("/{id}")
//    public BookingPerson getPerson(@PathVariable Integer id) {
//    	 log.info("GET /api/booking-persons/{} - fetch booking person", id);
//        return bookingPersonService.getPersonById(id);
//    }
//
//    @GetMapping
//    public List<BookingPerson> getAllPersons() {
//    	  log.info("GET /api/booking-persons - fetch all persons");
//        return bookingPersonService.getAllPersons();
//    }
//
//    @PutMapping("/{id}")
//    public BookingPerson updatePerson(@PathVariable Integer id, @Valid @RequestBody BookingPersonDto personDto) {
//    	 log.info("PUT /api/booking-persons/{} - update person", id);
//        BookingPerson person = bookingPersonService.getPersonById(id);
//        if (person != null) {
//            person.setAge(personDto.getAge());
//            person.setIsChild(personDto.getIsChild());
//        }
//        return bookingPersonService.updatePerson(person);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deletePerson(@PathVariable Integer id) {
//    	 log.info("DELETE /api/booking-persons/{} - delete person", id);
//        bookingPersonService.deletePerson(id);
//    }
//}

package com.hexaware.cozyhavenproject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.cozyhavenproject.dto.BookingPersonDto;
import com.hexaware.cozyhavenproject.entities.BookingPerson;
import com.hexaware.cozyhavenproject.entities.Booking;
import com.hexaware.cozyhavenproject.service.BookingPersonService;
import com.hexaware.cozyhavenproject.service.BookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/booking-persons")
@RequiredArgsConstructor
@Slf4j
public class BookingPersonController {

    private final BookingPersonService bookingPersonService;
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingPerson> createPerson(@Valid @RequestBody BookingPersonDto personDto) {
        log.info("Create booking person for bookingId {}", personDto.getBookingId());
        Booking booking = bookingService.getBookingById(personDto.getBookingId());
        BookingPerson person = new BookingPerson();
        person.setBooking(booking);
        person.setAge(personDto.getAge());
        person.setIsChild(personDto.getIsChild());
        BookingPerson created = bookingPersonService.createPerson(person);
        return ResponseEntity.created(URI.create("/api/booking-persons/" + created.getPersonId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingPerson> getPerson(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingPersonService.getPersonById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookingPerson>> getAllPersons() {
        return ResponseEntity.ok(bookingPersonService.getAllPersons());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookingPerson> updatePerson(@PathVariable Integer id, @Valid @RequestBody BookingPersonDto personDto) {
        BookingPerson person = bookingPersonService.getPersonById(id);
        Booking booking = bookingService.getBookingById(personDto.getBookingId());
        person.setBooking(booking);
        person.setAge(personDto.getAge());
        person.setIsChild(personDto.getIsChild());
        BookingPerson updated = bookingPersonService.updatePerson(person);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Integer id) {
        bookingPersonService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}

