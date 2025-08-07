package com.hexaware.cozyhavenproject.service;
import com.hexaware.cozyhavenproject.entities.Hotel;
import java.util.List;

public interface HotelService {

	 Hotel createHotel(Hotel hotel);
	    Hotel getHotelById(Integer id);
	    List<Hotel> getAllHotels();
	    Hotel updateHotel(Hotel hotel);
	    void deleteHotel(Integer id);
}
