package com.service;

import com.dao.RoomDAO;
import com.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomDAO roomDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRoom() {
        // Arrange
        Room room = new Room();
        room.setRoomNumber(101);
        roomService.saveRoom(room);
        verify(roomDAO, times(1)).save(room); 
    }

    @Test
    void testGetAllRooms() {
        Room room1 = new Room();
        Room room2 = new Room();
        when(roomDAO.findAll()).thenReturn(Arrays.asList(room1, room2));
        List<Room> rooms = roomService.getAllRooms();
        assertEquals(2, rooms.size());
    }

    @Test
    void testUpdateRoom_Success() {
        long id = 1L;
        Room existingRoom = new Room();
        existingRoom.setAvailable(true);
        existingRoom.setRoomNumber(101);
        
        when(roomDAO.findById(id)).thenReturn(Optional.of(existingRoom));

        Room updatedRoom = new Room();
        updatedRoom.setAvailable(false);
        updatedRoom.setRoomNumber(102);
        roomService.updateRoom(id, updatedRoom);

        assertFalse(existingRoom.isAvailable());
        assertEquals(102, existingRoom.getRoomNumber());
        verify(roomDAO, times(1)).save(existingRoom); 
    }

    @Test
    void testUpdateRoom_NotFound() {

        long id = 999L; 

        when(roomDAO.findById(id)).thenReturn(Optional.empty());

        Room updatedRoom = new Room();

      
       Exception exception = assertThrows(NoSuchElementException.class, () -> {
           roomService.updateRoom(id, updatedRoom);
       });

       assertEquals("No value present", exception.getMessage()); 
   }

   @Test
   void testDeleteRoom_Success() {
       // Arrange
       long id = 1L;
       
       Room roomToDelete = new Room();
       when(roomDAO.findById(id)).thenReturn(Optional.of(roomToDelete));

       // Act
       roomService.deleteRoom(id);

       verify(roomDAO, times(1)).delete(roomToDelete); 
   }

   @Test
   void testDeleteRoom_NotFound() {
      long id = 999L;
      when(roomDAO.findById(id)).thenReturn(Optional.empty());
      Exception exception = assertThrows(NoSuchElementException.class, () -> {
          roomService.deleteRoom(id);
      });

      assertEquals("No value present", exception.getMessage()); 
   }

   @Test
   void testFindRoomsByRoomNumber_Success() {
       int roomNumber = 101;
       Room room1 = new Room();
       room1.setRoomNumber(roomNumber);
       when(roomDAO.findByRoomNumber(roomNumber)).thenReturn(Arrays.asList(room1));
       List<Room> result = roomService.findRoomsByRoomNumber(roomNumber);
       assertEquals(1, result.size());
       assertEquals(roomNumber, result.get(0).getRoomNumber());
   }

   @Test 
   void testExistsById_Exists() {
      long id = 1L; 
      when(roomDAO.findById(id)).thenReturn(Optional.of(new Room()));

      boolean exists = roomService.existsById(id);

      assertTrue(exists);
   }
   
   @Test 
   void testExistsById_NotExists() {
      long id = 999L; 
      when(roomDAO.findById(id)).thenReturn(Optional.empty());

      boolean exists = roomService.existsById(id);

      assertFalse(exists);
   }

   @Test 
   void testGetRoomById_Success() {
      long id = 1L; 
      Room room = new Room(); 
      room.setAvailable(true); 

      when(roomDAO.findById(id)).thenReturn(Optional.of(room)); 

      Room result = roomService.getRoomById(id); 

      assertNotNull(result); 
      assertTrue(result.isAvailable()); 
   }

   @Test 
   void testGetRoomById_NotFound() {
      long id = 999L; 

      when(roomDAO.findById(id)).thenReturn(Optional.empty());

      Exception exception = assertThrows(NoSuchElementException.class, () -> {
          roomService.getRoomById(id);
      });

      assertEquals("No value present", exception.getMessage()); 
   }
}
