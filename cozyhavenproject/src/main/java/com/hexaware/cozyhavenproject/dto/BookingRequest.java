package com.hexaware.cozyhavenproject.dto;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookingRequest {

	@NotNull private Integer roomId;
    @NotNull @Future private LocalDate checkInDate;
    @NotNull @Future private LocalDate checkOutDate;

    @Min(1) private int adults = 1;
    @Min(0) private int children = 0;

    // Optional: Guest names/ages to create BookingPerson entries
    private List<GuestDTO> guests; // size should equal (adults+children)

    @Getter @Setter
    public static class GuestDTO {
        private String name;
        private Integer age; // null -> treat as adult if >14 logic not needed here because we have adults/children counts
    }
}
