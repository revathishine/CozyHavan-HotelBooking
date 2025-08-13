package com.hexaware.cozyhavenproject.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.cozyhavenproject.dto.BookingRequest;


@SpringBootTest
@AutoConfigureMockMvc
class BookingControllerIT {

	
	 @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private ObjectMapper objectMapper;

	    @Test
	    void createBooking_shouldReturnOk() throws Exception {
	        BookingRequest req = new BookingRequest();
	        req.setRoomId(1);
	        req.setAdults(2);
	        req.setChildren(1);
	        req.setCheckInDate(LocalDate.now().plusDays(5));
	        req.setCheckOutDate(LocalDate.now().plusDays(7));
	        req.setGuests(List.of());

	        mockMvc.perform(post("/api/v1/bookings")
	                .header("Authorization", "Bearer <valid-token>")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(req)))
	                .andExpect(status().isOk());
	    }

}
