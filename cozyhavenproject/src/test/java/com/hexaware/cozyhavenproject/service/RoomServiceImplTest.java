package com.hexaware.cozyhavenproject.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.hexaware.cozyhavenproject.entities.Room;
import com.hexaware.cozyhavenproject.exceptionhandler.ResourceNotFoundException;
import com.hexaware.cozyhavenproject.repository.RoomRepository;
import com.hexaware.cozyhavenproject.service.impl.RoomServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class RoomServiceImplTest {


	@InjectMocks
    private RoomServiceImpl service;

    @Mock
    private RoomRepository repository;

    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        room = new Room();
        room.setRoomId(1);
        
    }

    @Test
    void testCreateRoom() {
        when(repository.save(room)).thenReturn(room);
        assertEquals(room, service.createRoom(room));
    }

    @Test
    void testGetRoomById_Found() {
        when(repository.findById(1)).thenReturn(Optional.of(room));
        assertEquals(room, service.getRoomById(1));
    }

    @Test
    void testGetRoomById_NotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getRoomById(1));
    }

    @Test
    void testGetAllRooms() {
        when(repository.findAll()).thenReturn(List.of(room));
        assertEquals(1, service.getAllRooms().size());
    }

    @Test
    void testUpdateRoom() {
        when(repository.existsById(1)).thenReturn(true);
        when(repository.save(room)).thenReturn(room);
        assertEquals(room, service.updateRoom(room));
    }

    @Test
    void testDeleteRoom() {
        when(repository.existsById(1)).thenReturn(true);
        service.deleteRoom(1);
        verify(repository, times(1)).deleteById(1);
    }

}
