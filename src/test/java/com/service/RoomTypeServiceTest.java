package com.service;

import com.dao.RoomTypeDAO;
import com.model.RoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomTypeServiceTest {

    @InjectMocks
    private RoomTypeService roomTypeService;

    @Mock
    private RoomTypeDAO roomTypeDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRoomTypes() {
        RoomType roomType1 = new RoomType();
        roomType1.setRoomTypeId(1);
        roomType1.setTypeName("Deluxe");
        RoomType roomType2 = new RoomType();
        roomType2.setRoomTypeId(2);
        roomType2.setTypeName("Standard");
        when(roomTypeDAO.findAll()).thenReturn(Arrays.asList(roomType1, roomType2));
        var result = roomTypeService.getAllRoomType();
        assertEquals(2, result.size());
        assertEquals("Deluxe", result.get(0).getTypeName());
        assertEquals("Standard", result.get(1).getTypeName());
    }

    @Test
    void testSaveRoomType() {
        RoomType roomType = new RoomType();
        roomType.setRoomTypeId(1);
        roomType.setTypeName("Suite");
        roomTypeService.saveRoomType(roomType);
        verify(roomTypeDAO, times(1)).save(roomType); 
    }

    @Test
    void testUpdateRoomType_Success() {
        long id = 1L;
        RoomType existingRoomType = new RoomType();
        existingRoomType.setRoomTypeId(id); 
        when(roomTypeDAO.findById(id)).thenReturn(Optional.of(existingRoomType));
        RoomType updatedRoomType = new RoomType();
        updatedRoomType.setDescription("New Description");
        roomTypeService.updateRoomType(id, updatedRoomType);
        assertEquals("New Description", existingRoomType.getDescription());
        verify(roomTypeDAO, times(1)).save(existingRoomType); 
    }

    @Test
    void testUpdateRoomNotFound() {
        long id = 999L; 

        when(roomTypeDAO.findById(id)).thenReturn(Optional.empty());
        RoomType updatedRoom = new RoomType();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            roomTypeService.updateRoomType(id, updatedRoom);
        });

        assertEquals("RoomType not found for ID: 999", exception.getMessage());
    }

    @Test
    void testDeleteRoom_Success() {
        long id = 1L;
        RoomType roomToDelete = new RoomType();
        when(roomTypeDAO.findById(id)).thenReturn(Optional.of(roomToDelete));
        roomTypeService.deleteRoomType(id);
        verify(roomTypeDAO, times(1)).delete(roomToDelete); 
    }

    @Test
    void testDeleteNotFound() {
       long id = 999L; 
       when(roomTypeDAO.findById(id)).thenReturn(Optional.empty());
       Exception exception = assertThrows(Exception.class, () -> {
           roomTypeService.deleteRoomType(id);
       });

       assertTrue(exception instanceof NoSuchElementException); 
   }
    
   @Test
   void testExistsById_Exists() {
       long id = 1L;
       when(roomTypeDAO.existsById(id)).thenReturn(true);

       boolean exists = roomTypeService.existsById(id);

       assertTrue(exists);
   }
   
   @Test 
   void testExistsById_NotExists() {
       long id = 999L; 
       when(roomTypeDAO.existsById(id)).thenReturn(false);

       boolean exists = roomTypeService.existsById(id);

       assertFalse(exists);
   }
}
