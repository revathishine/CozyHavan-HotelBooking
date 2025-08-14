package com.hexaware.cozyhavenproject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.hexaware.cozyhavenproject.entities.Hotel;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.HotelRepository;
import com.hexaware.cozyhavenproject.service.impl.HotelServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceImplTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}

	 @InjectMocks
	    private HotelServiceImpl service;

	    @Mock
	    private HotelRepository repository;

	    private Hotel hotel;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	        hotel = new Hotel();
	        hotel.setHotelId(1);
	        hotel.setName("Cozy Haven");
	    }

	    @Test
	    void testCreateHotel() {
	        when(repository.save(hotel)).thenReturn(hotel);
	        assertEquals(hotel, service.createHotel(hotel));
	    }

	    @Test
	    void testGetHotelById_Found() {
	        when(repository.findById(1)).thenReturn(Optional.of(hotel));
	        assertEquals(hotel, service.getHotelById(1));
	    }

	    @Test
	    void testGetHotelById_NotFound() {
	        when(repository.findById(1)).thenReturn(Optional.empty());
	        assertThrows(ResourceNotFoundException.class, () -> service.getHotelById(1));
	    }

	    @Test
	    void testGetAllHotels() {
	        when(repository.findAll()).thenReturn(List.of(hotel));
	        assertEquals(1, service.getAllHotels().size());
	    }

	    @Test
	    void testUpdateHotel() {
	        when(repository.existsById(1)).thenReturn(true);
	        when(repository.save(hotel)).thenReturn(hotel);
	        assertEquals(hotel, service.updateHotel(hotel));
	    }

	    @Test
	    void testDeleteHotel() {
	        when(repository.existsById(1)).thenReturn(true);
	        service.deleteHotel(1);
	        verify(repository, times(1)).deleteById(1);
	    }
}
