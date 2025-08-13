package com.hexaware.cozyhavenproject.dto;

import java.math.BigDecimal;

import com.hexaware.cozyhavenproject.entities.BedType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RoomAvailabilityDto {

	 private Integer roomId;
	    private BedType bedType;
	    private int capacity;
	    private boolean available;
	    private BigDecimal perNightPriceForParty;
}
