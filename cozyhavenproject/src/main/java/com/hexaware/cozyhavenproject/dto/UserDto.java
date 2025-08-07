package com.hexaware.cozyhavenproject.dto;
import jakarta.validation.constraints.*;


public class UserDto {

	private Integer userId;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Pattern(regexp = "\\d{10}", message = "Contact number must be 10 digits")
    private String contactNumber;

    private String address;

    private String gender;

    @NotBlank(message = "Role is required")
    private String role;
}
