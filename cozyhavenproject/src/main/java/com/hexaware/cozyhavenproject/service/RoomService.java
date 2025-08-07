package com.hexaware.cozyhavenproject.service;
import com.hexaware.cozyhavenproject.entities.Room;
import java.util.List;

public interface RoomService {

	Room createRoom(Room room);
    Room getRoomById(Integer id);
    List<Room> getAllRooms();
    Room updateRoom(Room room);
    void deleteRoom(Integer id);
}
