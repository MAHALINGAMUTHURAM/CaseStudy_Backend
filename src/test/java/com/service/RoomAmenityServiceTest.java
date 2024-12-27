package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
import java.util.ArrayList;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.dao.RoomAmenityDAO;
import com.model.Amenity;
import com.model.Room;
import com.model.RoomAmenity;
import com.service.RoomAmenityService;
 
class RoomAmenityServiceTest {
 
    @InjectMocks
    private RoomAmenityService roomAmenityService;
 
    @Mock
    private RoomAmenityDAO roomAmenityDAO;
 
    private RoomAmenity roomAmenity;
    private Room room;
    private Amenity amenity;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Initialize test data
        room = new Room();
        room.setRoomId(1L);
 
        amenity = new Amenity();
        amenity.setAmenityId(1L);
        amenity.setName("WiFi");
 
        roomAmenity = new RoomAmenity();
        roomAmenity.setRoom(room);
        roomAmenity.setAmenity(amenity);
    }
 
    @Test
    void testSaveRoomAmenity() {
        roomAmenityService.saveRoomAmenity(roomAmenity);
 
        // Verify that roomAmenityDAO.save was called
        verify(roomAmenityDAO, times(1)).save(roomAmenity);
 
        // Verify that roomAmenity was added to the internal list
        assertTrue(roomAmenityService.findRoomAmenity(roomAmenity));
    }
 
    @Test
    void testFindRoomAmenity() {
        roomAmenityService.saveRoomAmenity(roomAmenity);
        boolean exists = roomAmenityService.findRoomAmenity(roomAmenity);
 
        assertTrue(exists); // Should return true as roomAmenity was saved
    }
 
    @Test
    void testGetRoomsByAmenity() {
        List<RoomAmenity> roomAmenities = new ArrayList<>();
        roomAmenities.add(roomAmenity);
 
        when(roomAmenityDAO.findByAmenity_AmenityId(1L)).thenReturn(roomAmenities);
 
        List<Room> rooms = roomAmenityService.getRoomsByAmenity(1L);
 
        // Assertions
        assertEquals(1, rooms.size());
        assertEquals(room.getRoomId(), rooms.get(0).getRoomId());
 
        // Verify method interactions
        verify(roomAmenityDAO, times(1)).findByAmenity_AmenityId(1L);
    }
 
    @Test
    void testGetAmenitiesByRoom() {
        List<RoomAmenity> roomAmenities = new ArrayList<>();
        roomAmenities.add(roomAmenity);
 
        when(roomAmenityDAO.findByRoom_RoomId(1L)).thenReturn(roomAmenities);
 
        List<Amenity> amenities = roomAmenityService.getAmenitiesByRoom(1L);
 
        // Assertions
        assertEquals(1, amenities.size());
        assertEquals(amenity.getAmenityId(), amenities.get(0).getAmenityId());
 
        // Verify method interactions
        verify(roomAmenityDAO, times(1)).findByRoom_RoomId(1L);
    }
}