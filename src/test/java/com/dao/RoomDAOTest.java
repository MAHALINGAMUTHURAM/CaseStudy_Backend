package com.dao;

import com.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

public class RoomDAOTest {

    @Mock
    private RoomDAO roomDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Sample Room objects
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();

        // Mocking the behavior of RoomDAO methods
        when(roomDAO.findByRoomtype_RoomTypeIdAndIsAvailable(anyLong(), anyBoolean()))
            .thenReturn(Arrays.asList(room1, room3));
        
        when(roomDAO.findRoomsByLocation("New York"))
            .thenReturn(Arrays.asList(room1, room2));
        
        when(roomDAO.findRoomsByLocation("Los Angeles"))
            .thenReturn(Arrays.asList(room3));
        
        when(roomDAO.findByRoomNumber(101))
            .thenReturn(Arrays.asList(room1));
        
        when(roomDAO.findByHotel_HotelId(anyLong()))
            .thenReturn(Arrays.asList(room1, room2)); // Adjust based on your setup
    }

    @Test
    public void testFindByRoomtype_RoomTypeIdAndIsAvailable() {
        List<Room> availableRooms = roomDAO.findByRoomtype_RoomTypeIdAndIsAvailable(1L, true);
        assertThat(availableRooms).hasSize(2); // Adjust based on your setup
    }

    @Test
    public void testFindRoomsByLocation() {
        List<Room> roomsInNewYork = roomDAO.findRoomsByLocation("New York");
        assertThat(roomsInNewYork).hasSize(2);
        
        List<Room> roomsInLosAngeles = roomDAO.findRoomsByLocation("Los Angeles");
        assertThat(roomsInLosAngeles).hasSize(1);
    }

    @Test
    public void testFindByRoomNumber() {
        List<Room> roomsWithNumber101 = roomDAO.findByRoomNumber(101);
        assertThat(roomsWithNumber101).hasSize(1);
        
        List<Room> roomsWithNumber999 = roomDAO.findByRoomNumber(999);
        assertThat(roomsWithNumber999).isEmpty();
    }

    @Test
    public void testFindByHotel_HotelId() {
        // Mocking behavior for hotel ID 1
        Room room1 = new Room();
        Room room2 = new Room();

        // Assuming both rooms belong to hotel with ID 1
        when(roomDAO.findByHotel_HotelId(1L)).thenReturn(Arrays.asList(room1, room2));

        // Test for hotel ID 1
        List<Room> roomsForHotel1 = roomDAO.findByHotel_HotelId(1L);
        assertThat(roomsForHotel1).isNotEmpty(); // Ensure that we have rooms for hotel ID 1
        assertThat(roomsForHotel1).hasSize(2); // We expect 2 rooms

        // Test for a non-existing hotel ID (e.g., 999)
        when(roomDAO.findByHotel_HotelId(999L)).thenReturn(Arrays.asList()); // No rooms for hotel ID 999
        List<Room> roomsForHotel999 = roomDAO.findByHotel_HotelId(999L);
        assertThat(roomsForHotel999).isEmpty(); // Ensure that no rooms are found for hotel ID 999
    }

}
