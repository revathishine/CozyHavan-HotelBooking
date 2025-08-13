package com.hexaware.cozyhavenproject.dto;


import java.math.BigDecimal;
import java.util.List;

import com.hexaware.cozyhavenproject.entities.BedType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {

	private Integer roomId;

    @NotNull(message = "Hotel ID is required")
    private Integer hotelId;

    private String roomSize;

    @NotNull(message = "Bed type is required")
    private BedType bedType;

    @Positive(message = "Max occupancy must be positive")
    private Integer maxOccupancy;

    @Positive(message = "Base fare must be positive")
    private BigDecimal baseFare;

    private Boolean isAc;
    private Boolean availabilityStatus;

    private List<Integer> bookingIds;
}
