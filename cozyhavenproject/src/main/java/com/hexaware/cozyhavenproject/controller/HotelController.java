package com.hexaware.cozyhavenproject.controller;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.hexaware.cozyhavenproject.dto.HotelDto;
import com.hexaware.cozyhavenproject.dto.HotelSearchRequest;
import com.hexaware.cozyhavenproject.dto.HotelSearchResultDto;
import com.hexaware.cozyhavenproject.dto.RoomAvailabilityDto;
import com.hexaware.cozyhavenproject.entities.Hotel;
import com.hexaware.cozyhavenproject.repository.BookingRepository;
import com.hexaware.cozyhavenproject.repository.HotelRepository;
import com.hexaware.cozyhavenproject.repository.RoomRepository;
import com.hexaware.cozyhavenproject.service.HotelService;
import com.hexaware.cozyhavenproject.util.PricingUtil;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j

@RestController
@RequestMapping("/api/hotels")
public class HotelController {


    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','HOTEL_OWNER')")
    public Hotel createHotel(@Valid @RequestBody HotelDto hotelDto) {
    	 log.info("POST /api/hotels - create hotel {}", hotelDto.getName());
        Hotel hotel = new Hotel();
        hotel.setName(hotelDto.getName());
        hotel.setLocation(hotelDto.getLocation());
        hotel.setDescription(hotelDto.getDescription());
        hotel.setAmenities(hotelDto.getAmenities());
        return hotelService.createHotel(hotel);
    }

    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable Integer id) {
    	 log.info("GET /api/hotels/{} - fetch hotel", id);
        return hotelService.getHotelById(id);
    }

    @GetMapping
    public List<Hotel> getAllHotels() {
    	 log.info("GET /api/hotels - fetch all hotels");
        return hotelService.getAllHotels();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','HOTEL_OWNER')")
    public Hotel updateHotel(@PathVariable Integer id, @Valid @RequestBody HotelDto  hotelDto) {
    	 log.info("PUT /api/hotels/{} - update hotel", id);
        Hotel hotel = hotelService.getHotelById(id);
        if (hotel != null) {
            hotel.setName(hotelDto.getName());
            hotel.setLocation(hotelDto.getLocation());
            hotel.setDescription(hotelDto.getDescription());
            hotel.setAmenities(hotelDto.getAmenities());
        }
        return hotelService.updateHotel(hotel);
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable Integer id) {
    	 log.info("DELETE /api/hotels/{} - delete hotel", id);
        hotelService.deleteHotel(id);
    }
    
    
    @PostMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<HotelSearchResultDto>> search(@Valid @RequestBody HotelSearchRequest req) {
        log.info("POST /api/hotels/search - search hotels in {}", req.getLocation());

        List<Hotel> hotels = hotelRepository.findByLocation(req.getLocation());
        long nights = ChronoUnit.DAYS.between(req.getCheckInDate(), req.getCheckOutDate());
        if (nights <= 0) {
			return ResponseEntity.ok(List.of());
		}

        List<HotelSearchResultDto> results = hotels.stream().map(h -> {
            List<Integer> bookedIds = bookingRepository.findBookedRoomIdsByHotelAndDateOverlap(
                    h.getHotelId(), req.getCheckInDate(), req.getCheckOutDate());

            List<RoomAvailabilityDto> roomDtos = roomRepository.findByHotel_HotelId(h.getHotelId())
                    .stream().map(r -> {
                        boolean available = !bookedIds.contains(r.getRoomId());
                        int capacity = PricingUtil.capacityByBedType(r.getBedType());
                        boolean capacityOk = (req.getAdults() + req.getChildren()) <= capacity;

                        BigDecimal perNight = PricingUtil.calculatePerNight(r, req.getAdults(), req.getChildren());
                        BigDecimal price = perNight.multiply(BigDecimal.valueOf(nights));

                        return new RoomAvailabilityDto(
                                r.getRoomId(),
                                r.getBedType(),
                                capacity,
                                available && capacityOk,
                                price
                        );
                    })
                    .filter(RoomAvailabilityDto::isAvailable)
                    .collect(Collectors.toList());

            return new HotelSearchResultDto(h.getHotelId(), h.getName(), h.getLocation(), roomDtos);
        }).filter(res -> !res.getRooms().isEmpty())
          .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }
    
    
    
    
    
    
    
    
    
    
    
}
