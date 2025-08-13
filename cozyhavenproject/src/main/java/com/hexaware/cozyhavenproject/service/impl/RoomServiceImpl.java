//package com.hexaware.cozyhavenproject.service.impl;
//import com.hexaware.cozyhavenproject.entities.Room;
//import com.hexaware.cozyhavenproject.repository.RoomRepository;
//import com.hexaware.cozyhavenproject.service.RoomService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RoomServiceImpl implements RoomService{
//
//	 @Autowired
//	    private RoomRepository roomRepository;
//
//	    public Room createRoom(Room room) {
//	        return roomRepository.save(room);
//	    }
//
//	    public Room getRoomById(Integer id) {
//	        return roomRepository.findById(id).orElse(null);
//	    }
//
//	    public List<Room> getAllRooms() {
//	        return roomRepository.findAll();
//	    }
//
//	    public Room updateRoom(Room room) {
//	        return roomRepository.save(room);
//	    }
//
//	    public void deleteRoom(Integer id) {
//	        roomRepository.deleteById(id);
//	    }
//}

package com.hexaware.cozyhavenproject.service.impl;

import com.hexaware.cozyhavenproject.entities.Room;
import com.hexaware.cozyhavenproject.repository.RoomRepository;
import com.hexaware.cozyhavenproject.service.RoomService;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService{

    @Autowired
    private RoomRepository roomRepository;

    @Override
	public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
	public Room getRoomById(Integer id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
    }

    @Override
	public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
	public Room updateRoom(Room room) {
        Integer id = room.getRoomId();
        if (id == null || !roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room not found with id: " + id);
        }
        return roomRepository.save(room);
    }

    @Override
	public void deleteRoom(Integer id) {
        if (!roomRepository.existsById(id)) {
            throw new ResourceNotFoundException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }
}
