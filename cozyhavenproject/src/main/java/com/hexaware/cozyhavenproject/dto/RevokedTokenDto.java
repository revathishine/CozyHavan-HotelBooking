package com.hexaware.cozyhavenproject.dto;


import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevokedTokenDto {

	
	 private Integer tokenId;

	    @NotNull(message = "User ID is required")
	    private Integer userId;

	    @NotBlank(message = "Token cannot be empty")
	    private String token;

	    private LocalDateTime revokedAt;
}
