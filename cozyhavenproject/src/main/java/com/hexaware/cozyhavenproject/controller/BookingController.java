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
//import com.hexaware.cozyhavenproject.dto.BookingDto;
//import com.hexaware.cozyhavenproject.entities.Booking;
//import com.hexaware.cozyhavenproject.service.BookingService;
//
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//
//@RestController
//@RequestMapping("/api/bookings")
//public class BookingController {
//
//	
//	  @Autowired
//	    private BookingService bookingService;
//
//	    @PostMapping
//	    public Booking createBooking(@Valid @RequestBody BookingDto bookingDto) {
//	    	 log.info("POST /api/bookings - create booking for user {}", bookingDto.getUserId());
//	        Booking booking = new Booking();
//	        booking.setCheckInDate(bookingDto.getCheckInDate());
//	        booking.setCheckOutDate(bookingDto.getCheckOutDate());
//	        booking.setNumAdults(bookingDto.getNumAdults());
//	        booking.setNumChildren(bookingDto.getNumChildren());
//	        booking.setTotalPrice(bookingDto.getTotalPrice());
//	        booking.setStatus(bookingDto.getStatus());
//	        return bookingService.createBooking(booking);
//	    }
//
//	    @GetMapping("/{id}")
//	    public Booking getBooking(@PathVariable Integer id) {
//	    	 log.info("GET /api/bookings/{} - fetch booking", id);
//	        return bookingService.getBookingById(id);
//	    }
//
//	    @GetMapping
//	    public List<Booking> getAllBookings() {
//	    	 log.info("GET /api/bookings - fetch all bookings");
//	        return bookingService.getAllBookings();
//	    }
//
//	    @PutMapping("/{id}")
//	    public Booking updateBooking(@PathVariable Integer id, @Valid @RequestBody BookingDto bookingDto) {
//	    	log.info("PUT /api/bookings/{} - update booking", id);
//	        Booking booking = bookingService.getBookingById(id);
//	        if (booking != null) {
//	            booking.setCheckInDate(bookingDto.getCheckInDate());
//	            booking.setCheckOutDate(bookingDto.getCheckOutDate());
//	            booking.setNumAdults(bookingDto.getNumAdults());
//	            booking.setNumChildren(bookingDto.getNumChildren());
//	            booking.setTotalPrice(bookingDto.getTotalPrice());
//	            booking.setStatus(bookingDto.getStatus());
//	        }
//	        return bookingService.updateBooking(booking);
//	    }
//
//	    @DeleteMapping("/{id}")
//	    public void deleteBooking(@PathVariable Integer id) {
//	    	 log.info("DELETE /api/bookings/{} - delete booking", id);
//	        bookingService.deleteBooking(id);
//	    }
//}

//package com.hexaware.cozyhavenproject.controller;
//
//import java.net.URI;
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.hexaware.cozyhavenproject.dto.BookingDto;
//import com.hexaware.cozyhavenproject.entities.Booking;
//import com.hexaware.cozyhavenproject.entities.User;
//import com.hexaware.cozyhavenproject.entities.Room;
//import com.hexaware.cozyhavenproject.service.BookingService;
//import com.hexaware.cozyhavenproject.service.UserService;
//import com.hexaware.cozyhavenproject.service.RoomService;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@RestController
//@RequestMapping("/api/bookings")
//@RequiredArgsConstructor
//@Slf4j
//public class BookingController {
//
//    private final BookingService bookingService;
//    private final UserService userService;
//    private final RoomService roomService;
//    
//
//    @PostMapping
//    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingDto bookingDto) {
//        log.info("Create booking for user {} room {}", bookingDto.getUserId(), bookingDto.getRoomId());
//
//        // basic date validation
//        LocalDate ci = bookingDto.getCheckInDate();
//        LocalDate co = bookingDto.getCheckOutDate();
//        if (ci == null || co == null || !ci.isBefore(co)) {
//            throw new IllegalArgumentException("Check-in must be before check-out and both required");
//        }
//
//        User user = userService.getUserById(bookingDto.getUserId());
//        Room room = roomService.getRoomById(bookingDto.getRoomId());
//
//        Booking booking = new Booking();
//        booking.setUser(user);
//        booking.setRoom(room);
//        booking.setCheckInDate(ci);
//        booking.setCheckOutDate(co);
//        booking.setNumAdults(bookingDto.getNumAdults());
//        booking.setNumChildren(bookingDto.getNumChildren());
//        booking.setTotalPrice(bookingDto.getTotalPrice());
//        booking.setStatus(bookingDto.getStatus());
//
//        Booking created = bookingService.createBooking(booking);
//        return ResponseEntity.created(URI.create("/api/bookings/" + created.getBookingId())).body(created);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Booking> getBooking(@PathVariable Integer id) {
//        return ResponseEntity.ok(bookingService.getBookingById(id));
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Booking>> getAllBookings() {
//        return ResponseEntity.ok(bookingService.getAllBookings());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id, @Valid @RequestBody BookingDto bookingDto) {
//        log.info("Update booking {}", id);
//        Booking booking = bookingService.getBookingById(id);
//
//        // example check
//        if (!booking.getCheckInDate().isBefore(bookingDto.getCheckOutDate())) {
//            throw new IllegalArgumentException("Check-in must be before check-out");
//        }
//
//        booking.setCheckInDate(bookingDto.getCheckInDate());
//        booking.setCheckOutDate(bookingDto.getCheckOutDate());
//        booking.setNumAdults(bookingDto.getNumAdults());
//        booking.setNumChildren(bookingDto.getNumChildren());
//        booking.setTotalPrice(bookingDto.getTotalPrice());
//        booking.setStatus(bookingDto.getStatus());
//
//        Booking updated = bookingService.updateBooking(booking);
//        return ResponseEntity.ok(updated);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
//        bookingService.deleteBooking(id);
//        return ResponseEntity.noContent().build();
//    }
//}





package com.hexaware.cozyhavenproject.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.hexaware.cozyhavenproject.dto.BookingDto;
import com.hexaware.cozyhavenproject.dto.BookingRequest;
import com.hexaware.cozyhavenproject.dto.BookingResponse;
import com.hexaware.cozyhavenproject.entities.Booking;
import com.hexaware.cozyhavenproject.entities.BookingPerson;
import com.hexaware.cozyhavenproject.entities.BookingStatus;
import com.hexaware.cozyhavenproject.entities.Room;
import com.hexaware.cozyhavenproject.entities.User;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.BookingPersonRepository;
import com.hexaware.cozyhavenproject.repository.BookingRepository;
import com.hexaware.cozyhavenproject.repository.RoomRepository;
import com.hexaware.cozyhavenproject.repository.UserRepository;
import com.hexaware.cozyhavenproject.security.CustomerUserDetails;
import com.hexaware.cozyhavenproject.service.BookingService;
import com.hexaware.cozyhavenproject.util.PricingUtil;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private BookingPersonRepository bookingPersonRepository;

    // -------- New Create Booking with Business Logic --------
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponse> createBooking(
            @AuthenticationPrincipal CustomerUserDetails principal,
            @Valid @RequestBody BookingRequest req) {

        User user = userRepository.findByEmail(principal.getUsername());
        if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}

        Room room = roomRepository.findById(req.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        boolean overlap = bookingRepository.existsActiveOverlap(room.getRoomId(), req.getCheckInDate(), req.getCheckOutDate());
        if (overlap) {
			throw new IllegalArgumentException("Room not available for selecte d dates");
		}

        int capacity = PricingUtil.capacityByBedType(room.getBedType());
        if ((req.getAdults() + req.getChildren()) > capacity) {
            throw new IllegalArgumentException("Party exceeds room capacity");
        }

        long nights = ChronoUnit.DAYS.between(req.getCheckInDate(), req.getCheckOutDate());
        if (nights <= 0) {
			throw new IllegalArgumentException("Invalid check-in/check-out dates");
		}

        BigDecimal perNight = PricingUtil.calculatePerNight(room, req.getAdults(), req.getChildren());
        BigDecimal total = perNight.multiply(BigDecimal.valueOf(nights));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setCheckInDate(req.getCheckInDate());
        booking.setCheckOutDate(req.getCheckOutDate());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setTotalPrice(total);
        Booking saved = bookingRepository.save(booking);

        List<Integer> personIds = List.of();
        if (req.getGuests() != null && !req.getGuests().isEmpty()) {
            personIds = req.getGuests().stream().map(g -> {
                BookingPerson p = new BookingPerson();
                p.setBooking(saved);
                p.setName(g.getName());
                p.setAge(g.getAge());
                return bookingPersonRepository.save(p).getPersonId();
            }).toList();
        }

        BookingResponse resp = new BookingResponse(
                saved.getBookingId(),
                room.getRoomId(),
                user.getUserId(),
                saved.getCheckInDate(),
                saved.getCheckOutDate(),
                saved.getStatus().name(),
                saved.getTotalPrice(),
                personIds
        );
        return ResponseEntity.ok(resp);
    }

    // -------- Cancel Booking with Refund --------
    @PostMapping("/{bookingId}/cancel")
    @PreAuthorize("hasAnyRole('USER','HOTEL_OWNER','ADMIN')")
    public ResponseEntity<BookingResponse> cancelBooking(
            @PathVariable Integer bookingId,
            @AuthenticationPrincipal CustomerUserDetails principal) {

        Booking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        boolean isAdminOrOwner = principal.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_HOTEL_OWNER"));
        if (!isAdminOrOwner && !b.getUser().getEmail().equals(principal.getUsername())) {
            return ResponseEntity.status(403).build();
        }

        BigDecimal refund = BigDecimal.ZERO;
        if (LocalDate.now().isBefore(b.getCheckInDate())) {
            refund = b.getTotalPrice();
        } else {
            refund = b.getTotalPrice().multiply(BigDecimal.valueOf(0.50));
        }

        b.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(b);

        BookingResponse resp = new BookingResponse(
                b.getBookingId(),
                b.getRoom().getRoomId(),
                b.getUser().getUserId(),
                b.getCheckInDate(),
                b.getCheckOutDate(),
                b.getStatus().name(),
                b.getTotalPrice(),
                List.of()
        );
        return ResponseEntity.ok(resp);
    }

    // -------- Your Original CRUD Methods --------
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Integer id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Integer id, @Valid @RequestBody BookingDto bookingDto) {
        Booking booking = bookingService.getBookingById(id);
        if (!booking.getCheckInDate().isBefore(bookingDto.getCheckOutDate())) {
            throw new IllegalArgumentException("Check-in must be before check-out");
        }

        booking.setCheckInDate(bookingDto.getCheckInDate());
        booking.setCheckOutDate(bookingDto.getCheckOutDate());
        booking.setNumAdults(bookingDto.getNumAdults());
        booking.setNumChildren(bookingDto.getNumChildren());
        booking.setTotalPrice(bookingDto.getTotalPrice());
        booking.setStatus(bookingDto.getStatus());

        Booking updated = bookingService.updateBooking(booking);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
}




