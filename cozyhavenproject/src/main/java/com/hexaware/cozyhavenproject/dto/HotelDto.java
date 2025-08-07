package com.hexaware.cozyhavenproject.dto;

import jakarta.validation.constraints.*;
//dto class 

public class HotelDto {

	private Integer hotelId;

    @NotBlank(message = "Hotel name is required")
    private String name;

    @NotBlank(message = "Location is required")
    private String location;

    private String description;

    private Integer ownerId;

    private String amenities;
}
