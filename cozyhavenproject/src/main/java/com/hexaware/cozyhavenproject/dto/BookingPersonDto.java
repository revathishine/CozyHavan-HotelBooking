package com.hexaware.cozyhavenproject.dto;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingPersonDto {

    private Integer personId;

    @NotNull(message = "Booking ID is required")
    private Integer bookingId;

    
    @NotNull(message = "Name is required") // ✅ Added validation
    private String name;
    
    @Positive(message = "Age must be positive")
    private Integer age;

    private Boolean isChild;

}
