package com.hexaware.cozyhavenproject.dto.auth;

import com.hexaware.cozyhavenproject.entities.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

	@NotBlank private String name;
    @Email @NotBlank private String email;
    @NotBlank private String password;
    private String contactNumber;
    private String address;
    private String gender;
    @NotNull private Role role;
}
