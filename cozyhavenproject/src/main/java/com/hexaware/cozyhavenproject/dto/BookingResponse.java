package com.hexaware.cozyhavenproject.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class BookingResponse {

	private Integer bookingId;
    private Integer roomId;
    private Integer userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String status;
    private BigDecimal totalAmount;
    private List<Integer> personIds;
}
