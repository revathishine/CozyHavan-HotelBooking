package com.hexaware.cozyhavenproject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.hexaware.cozyhavenproject.repository.BookingRepository;

class BookingServiceTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}
	
	 private BookingRepository bookingRepository;

	    @BeforeEach
	    void setUp() {
	        bookingRepository = Mockito.mock(BookingRepository.class);
	    }

	    @Test
	    void overlapCheck_shouldReturnTrueIfBookingExists() {
	        when(bookingRepository.existsActiveOverlap(1, LocalDate.now(), LocalDate.now().plusDays(2)))
	                .thenReturn(true);

	        boolean overlap = bookingRepository.existsActiveOverlap(1, LocalDate.now(), LocalDate.now().plusDays(2));
	        assertThat(overlap).isTrue();
	    }

}
