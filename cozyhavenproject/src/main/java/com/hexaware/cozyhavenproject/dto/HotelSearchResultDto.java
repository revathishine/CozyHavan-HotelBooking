package com.hexaware.cozyhavenproject.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class HotelSearchResultDto {

	 private Integer hotelId;
	    private String hotelName;
	    private String location;
	    private List<RoomAvailabilityDto> rooms;
}
