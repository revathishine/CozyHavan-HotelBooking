//package com.hexaware.cozyhavenproject.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.hexaware.cozyhavenproject.dto.RoomDto;
//import com.hexaware.cozyhavenproject.entities.Room;
//import com.hexaware.cozyhavenproject.service.RoomService;
//
//import jakarta.validation.Valid;
//
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//
//@RestController
//@RequestMapping("/api/rooms")
//public class RoomController {
//
//	
//	@Autowired
//    private RoomService roomService;
//
//    @PostMapping
//    public Room createRoom(@Valid @RequestBody RoomDto roomDto) {
//    	 log.info("POST /api/rooms - create room in hotel {}", roomDto.getHotelId());
//        Room room = new Room();
//        room.setRoomSize(roomDto.getRoomSize());
//        room.setBedType(roomDto.getBedType());
//        room.setMaxOccupancy(roomDto.getMaxOccupancy());
//        room.setBaseFare(roomDto.getBaseFare());
//        room.setIsAc(roomDto.getIsAc());
//        room.setAvailabilityStatus(roomDto.getAvailabilityStatus());
//        return roomService.createRoom(room);
//    }
//
//    @GetMapping("/{id}")
//    public Room getRoom(@PathVariable Integer id) {
//    	 log.info("GET /api/rooms/{} - fetch room", id);
//        return roomService.getRoomById(id);
//    }
//
//    @GetMapping
//    public List<Room> getAllRooms() {
//    	 log.info("GET /api/rooms - fetch all rooms");
//        return roomService.getAllRooms();
//    }
//
//    @PutMapping("/{id}")
//    public Room updateRoom(@PathVariable Integer id, @Valid @RequestBody RoomDto roomDto) {
//    	log.info("PUT /api/rooms/{} - update room", id);
//        Room room = roomService.getRoomById(id);
//        if (room != null) {
//            room.setRoomSize(roomDto.getRoomSize());
//            room.setBedType(roomDto.getBedType());
//            room.setMaxOccupancy(roomDto.getMaxOccupancy());
//            room.setBaseFare(roomDto.getBaseFare());
//            room.setIsAc(roomDto.getIsAc());
//            room.setAvailabilityStatus(roomDto.getAvailabilityStatus());
//        }
//        return roomService.updateRoom(room);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteRoom(@PathVariable Integer id) {
//    	 log.info("DELETE /api/rooms/{} - delete room", id);
//        roomService.deleteRoom(id);
//    }
//}

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
