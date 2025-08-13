
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

