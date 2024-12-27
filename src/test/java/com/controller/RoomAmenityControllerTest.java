package com.controller;

import com.exception.CustomException;
import com.exception.Response;
import com.model.Amenity;
import com.model.Room;
import com.model.RoomAmenity;
import com.service.RoomAmenityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomAmenityControllerTest {

    @InjectMocks
    private RoomAmenityController roomAmenityController;

    @Mock
    private RoomAmenityService roomAmenityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to create a mock RoomAmenity
    private RoomAmenity createMockRoomAmenity(Long roomId, Long amenityId) {
        Room room = new Room();
        room.setRoomId(roomId); // Assuming Room has a method setRoomId

        Amenity amenity = new Amenity();
        amenity.setAmenityId(amenityId); // Assuming Amenity has a method setAmenityId

        RoomAmenity roomAmenity = new RoomAmenity();
        roomAmenity.setRoom(room);
        roomAmenity.setAmenity(amenity);
        
        return roomAmenity;
    }

    @Test
    void testCreateRoomAmenitySuccess() throws CustomException {
        // Arrange
        RoomAmenity roomAmenity = createMockRoomAmenity(1L, 1L);

        // Mocking the service behavior
        when(roomAmenityService.findRoomAmenity(roomAmenity)).thenReturn(false); // Amenity does not exist
        doNothing().when(roomAmenityService).saveRoomAmenity(roomAmenity); // Mock save behavior

        // Act
        ResponseEntity<Object> response = roomAmenityController.createRoomAmenity(roomAmenity);

        // Assert
        assertEquals(201, response.getStatusCodeValue()); // Validate status code
        assertNotNull(response.getBody());
        
        Response responseBody = (Response) response.getBody();
        assertEquals("POSTSUCCESS", responseBody.getCode()); // Validate success code
        assertEquals("RoomAmenity added successfully", responseBody.getMessage()); // Validate success message
        
        verify(roomAmenityService, times(1)).saveRoomAmenity(roomAmenity); // Ensure save was called once
    }

    @Test
    void testCreateRoomAmenityFailure() {
        // Arrange
        RoomAmenity roomAmenity = createMockRoomAmenity(1L, 1L);

        // Mocking the service behavior to indicate that the amenity already exists
        when(roomAmenityService.findRoomAmenity(roomAmenity)).thenReturn(true); // Amenity exists

        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            roomAmenityController.createRoomAmenity(roomAmenity);
        });

        assertEquals("ADDFAILS", exception.getCode()); // Validate exception code
        assertEquals("RoomAmenity already exists", exception.getMessage()); // Validate exception message
        
        verify(roomAmenityService, times(0)).saveRoomAmenity(any()); // Ensure save was not called
    }
}
