package com.hexaware.cozyhavenproject.dto;




import java.time.LocalDateTime;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

	

    private Integer reviewId;

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Hotel ID is required")
    private Integer hotelId;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;

    private LocalDateTime reviewDate;
}
