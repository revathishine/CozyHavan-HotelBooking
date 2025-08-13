package com.hexaware.cozyhavenproject.dto;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.hexaware.cozyhavenproject.entities.BookingStatus;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookingDto {

	
	private Integer bookingId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Room ID is required")
    private Integer roomId;

    @NotNull(message = "Check-in date is required")
    @Future(message = "Check-in date must be in the future")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Positive(message = "Number of adults must be positive")
    private Integer numAdults;

    private Integer numChildren;

    @Positive(message = "Total price must be positive")
    private BigDecimal totalPrice;

    private LocalDateTime bookingDate;

    @NotNull(message = "Booking status is required")
    private BookingStatus status;

    private List<Integer> bookingPersonIds;
}
