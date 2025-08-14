//date:4/8/25 author

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

    
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponse> createBooking(
            @AuthenticationPrincipal CustomerUserDetails principal,
            @Valid @RequestBody BookingRequest req) {

        User user = userRepository.findByEmail(principal.getUsername());
        if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}
        
        // Explicit date checks
        if (!req.getCheckInDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date must be after today");
        }
        if (!req.getCheckOutDate().isAfter(req.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
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

    // Cancel Booking with Refun
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

    // crud
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




