package com.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.exception.CustomException;
import com.model.Room;
import com.model.RoomType;
import com.service.RoomAmenityService;
import com.service.RoomService;
import com.service.TaskService;
import com.exception.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class RoomControllerTest {

    @Mock
    private RoomService roomService;

    @Mock
    private TaskService taskService;

    @Mock
    private RoomAmenityService roomAmenityService;

    @InjectMocks
    private RoomController roomController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoom_Success() throws CustomException {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");
        Room room = createMockRoom(1L, 101, true, roomType);

        when(roomService.findRoomsByRoomNumber(room.getRoomNumber())).thenReturn(new ArrayList<>());
        doNothing().when(roomService).saveRoom(room);

        ResponseEntity<?> response = roomController.createRoom(room);

        verify(roomService, times(1)).saveRoom(room);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("POSTSUCCESS", ((Response) response.getBody()).getCode());
    }

    @Test
    void testCreateRoom_AlreadyExists() {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");
        Room room = createMockRoom(1L, 101, true, roomType);

        when(roomService.findRoomsByRoomNumber(room.getRoomNumber())).thenReturn(List.of(room));

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomController.createRoom(room);
        });

        assertEquals("ADDFAILS", exception.getCode());
    }

    @Test
    void testGetAllRooms_Success() throws CustomException {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");
        Room room = createMockRoom(1L, 101, true, roomType);

        when(roomService.getAllRooms()).thenReturn(List.of(room));

        ResponseEntity<?> response = roomController.getAllRooms();

        verify(roomService, times(1)).getAllRooms();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(((List<?>) response.getBody()).isEmpty());
    }

    @Test
    void testGetAllRooms_EmptyList() {
        when(roomService.getAllRooms()).thenReturn(new ArrayList<>());

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomController.getAllRooms();
        });

        assertEquals("GETALLFAILS", exception.getCode());
    }

    @Test
    void testGetRoomById_Success() throws CustomException {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");
        Room room = createMockRoom(1L, 101, true, roomType);

        when(roomService.existsById(room.getRoomId())).thenReturn(true);
        when(roomService.getRoomById(room.getRoomId())).thenReturn(room);

        ResponseEntity<?> response = roomController.getRoomById(room.getRoomId());

        verify(roomService, times(1)).getRoomById(room.getRoomId());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(room, response.getBody());
    }

    @Test
    void testGetRoomById_NotFound() {
        when(roomService.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomController.getRoomById(1L);
        });

        assertEquals("GETFAILS", exception.getCode());
    }

    // Utility methods for creating mock objects
    private Room createMockRoom(long id, int roomNumber, boolean isAvailable, RoomType roomType) {
        Room room = new Room();
        room.setRoomId(id);
        room.setRoomNumber(roomNumber);
        room.setAvailable(isAvailable);
        room.setRoomtype(roomType);
        return room;
    }

    private RoomType createMockRoomType(long id, String typeName, String description) {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(id);
        roomType.setTypeName(typeName);
        roomType.setDescription(description);
        return roomType;
    }
}
