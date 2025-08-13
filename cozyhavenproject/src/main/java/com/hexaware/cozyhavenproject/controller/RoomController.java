//date4/8/25


package com.hexaware.cozyhavenproject.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.cozyhavenproject.dto.RoomDto;
import com.hexaware.cozyhavenproject.entities.Room;
import com.hexaware.cozyhavenproject.entities.Hotel;
import com.hexaware.cozyhavenproject.service.RoomService;
import com.hexaware.cozyhavenproject.service.HotelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@Valid @RequestBody RoomDto roomDto) {
        log.info("Create room for hotelId {}", roomDto.getHotelId());
        Hotel hotel = hotelService.getHotelById(roomDto.getHotelId());
        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomSize(roomDto.getRoomSize());
        room.setBedType(roomDto.getBedType());
        room.setMaxOccupancy(roomDto.getMaxOccupancy());
        room.setBaseFare(roomDto.getBaseFare());
        room.setIsAc(roomDto.getIsAc());
        room.setAvailabilityStatus(roomDto.getAvailabilityStatus());
        Room created = roomService.createRoom(room);
        return ResponseEntity.created(URI.create("/api/rooms/" + created.getRoomId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Integer id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Integer id, @Valid @RequestBody RoomDto roomDto) {
        log.info("Update room {}", id);
        Room room = roomService.getRoomById(id);
        Hotel hotel = hotelService.getHotelById(roomDto.getHotelId());
        room.setHotel(hotel);
        room.setRoomSize(roomDto.getRoomSize());
        room.setBedType(roomDto.getBedType());
        room.setMaxOccupancy(roomDto.getMaxOccupancy());
        room.setBaseFare(roomDto.getBaseFare());
        room.setIsAc(roomDto.getIsAc());
        room.setAvailabilityStatus(roomDto.getAvailabilityStatus());
        Room updated = roomService.updateRoom(room);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
