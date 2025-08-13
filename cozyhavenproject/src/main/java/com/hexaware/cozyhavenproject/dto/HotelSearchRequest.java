package com.hexaware.cozyhavenproject.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HotelSearchRequest {

	 @NotBlank private String location;
	    @NotNull @Future private LocalDate checkInDate;
	    @NotNull @Future private LocalDate checkOutDate;
	    @Min(1) private int rooms = 1;
	    @Min(0) private int adults = 1;
	    @Min(0) private int children = 0;
}
