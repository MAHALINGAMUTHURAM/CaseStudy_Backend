package com.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.exception.CustomException;
import com.exception.Response;
import com.model.RoomType;
import com.service.RoomTypeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

class RoomTypeControllerTest {

    @Mock
    private RoomTypeService roomTypeService;

    @InjectMocks
    private RoomTypeController roomTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoomType_Success() throws CustomException {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");

        when(roomTypeService.findRoomType(roomType)).thenReturn(false);
        doNothing().when(roomTypeService).saveRoomType(roomType);

        ResponseEntity<Object> response = roomTypeController.createRoomType(roomType);

        verify(roomTypeService, times(1)).saveRoomType(roomType);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("POSTSUCCESS", ((Response) response.getBody()).getCode());
    }

    @Test
    void testCreateRoomType_AlreadyExists() {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");

        when(roomTypeService.findRoomType(roomType)).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomTypeController.createRoomType(roomType);
        });

        assertEquals("ADDFAILS", exception.getCode());
        verify(roomTypeService, never()).saveRoomType(any(RoomType.class));
    }

    @Test
    void testGetRoomTypeById_Success() throws CustomException {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");

        when(roomTypeService.existsById(roomType.getRoomTypeId())).thenReturn(true);
        when(roomTypeService.getRoomTypeById(roomType.getRoomTypeId())).thenReturn(roomType);

        ResponseEntity<Object> response = roomTypeController.getRoomTypeById(roomType.getRoomTypeId());

        verify(roomTypeService, times(1)).getRoomTypeById(roomType.getRoomTypeId());
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(roomType, response.getBody());
    }

    @Test
    void testGetRoomTypeById_NotFound() {
        when(roomTypeService.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomTypeController.getRoomTypeById(1L);
        });

        assertEquals("GETFAILS", exception.getCode());
    }

    @Test
    void testGetAllRoomTypes_Success() throws CustomException {
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");

        when(roomTypeService.getAllRoomType()).thenReturn(List.of(roomType));

        ResponseEntity<Object> response = roomTypeController.getAllRoomTypes();

        verify(roomTypeService, times(1)).getAllRoomType();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(((List<?>) response.getBody()).isEmpty());
    }

    @Test
    void testGetAllRoomTypes_EmptyList() {
        when(roomTypeService.getAllRoomType()).thenReturn(new ArrayList<>());

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomTypeController.getAllRoomTypes();
        });

        assertEquals("GETFAILS", exception.getCode());
    }

    @Test
    void testDeleteRoomType_Success() throws CustomException {
        long roomTypeId = 1L;

        when(roomTypeService.existsById(roomTypeId)).thenReturn(true);
        doNothing().when(roomTypeService).deleteRoomType(roomTypeId);

        ResponseEntity<Object> response = roomTypeController.deleteRoomType(roomTypeId);

        verify(roomTypeService, times(1)).deleteRoomType(roomTypeId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("DELETESUCCESS", ((Response) response.getBody()).getCode());
    }

    @Test
    void testDeleteRoomType_NotFound() {
        long roomTypeId = 1L;

        when(roomTypeService.existsById(roomTypeId)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomTypeController.deleteRoomType(roomTypeId);
        });

        assertEquals("DLTFAILS", exception.getCode());
    }

    @Test
    void testUpdateRoomType_Success() throws CustomException {
        long roomTypeId = 1L;
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");

        when(roomTypeService.existsById(roomTypeId)).thenReturn(true);
        doNothing().when(roomTypeService).updateRoomType(roomTypeId, roomType);

        ResponseEntity<Object> response = roomTypeController.updateRoomType(roomTypeId, roomType);

        verify(roomTypeService, times(1)).updateRoomType(roomTypeId, roomType);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("UPDATESUCCESS", ((Response) response.getBody()).getCode());
    }

    @Test
    void testUpdateRoomType_NotFound() {
        long roomTypeId = 1L;
        RoomType roomType = createMockRoomType(1L, "Deluxe", "Luxury room with premium facilities");

        when(roomTypeService.existsById(roomTypeId)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            roomTypeController.updateRoomType(roomTypeId, roomType);
        });

        assertEquals("UPDTFAILS", exception.getCode());
    }

    // Utility method for creating mock RoomType
    private RoomType createMockRoomType(long id, String typeName, String description) {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(id);
        roomType.setTypeName(typeName);
        roomType.setDescription(description);
        return roomType;
    }
}

