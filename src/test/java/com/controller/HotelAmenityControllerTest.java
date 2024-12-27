package com.controller;

import com.controller.HotelAmenityController;
import com.exception.CustomException;
import com.exception.Response;
import com.model.Amenity;
import com.model.Hotel;
import com.model.HotelAmenity;
import com.service.HotelAmenityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class HotelAmenityControllerTest {

    @InjectMocks
    private HotelAmenityController hotelAmenityController;

    @Mock
    private HotelAmenityService hotelAmenityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateHotelAmenity_Success() throws CustomException {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L); // Set properties as necessary

        Amenity amenity = new Amenity();
        amenity.setAmenityId(1L); // Set properties as necessary

        HotelAmenity hotelAmenity = new HotelAmenity();
        hotelAmenity.setHotel(hotel); // Set the hotel
        hotelAmenity.setAmenity(amenity); // Set the amenity

        // Mocking the service behavior
        when(hotelAmenityService.findHotelAmenity(hotelAmenity)).thenReturn(false);
        
        // Act
        ResponseEntity<Object> response = hotelAmenityController.createHotelAmenity(hotelAmenity);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Response responseBody = (Response) response.getBody(); // Cast to Response
        assertNotNull(responseBody); // Ensure response body is not null
        assertEquals("POSTSUCCESS", responseBody.getCode()); // Check status
        assertEquals("HotelAmenity added successfully", responseBody.getMessage()); // Check message
        
        // Verify that save method was called
        verify(hotelAmenityService).saveHotelAmenity(hotelAmenity);
    }

    @Test
    void testCreateHotelAmenity_Failure_AlreadyExists() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);

        Amenity amenity = new Amenity();
        amenity.setAmenityId(1L);

        HotelAmenity hotelAmenity = new HotelAmenity();
        hotelAmenity.setHotel(hotel);
        hotelAmenity.setAmenity(amenity);

        when(hotelAmenityService.findHotelAmenity(hotelAmenity)).thenReturn(true);

        // Act & Assert
        CustomException thrown = org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
            hotelAmenityController.createHotelAmenity(hotelAmenity);
        });

        assertEquals("ADDFAILS", thrown.getCode());
        assertEquals("HotelAmenity already exists", thrown.getMessage());
        
        // Verify that save method was not called
        verify(hotelAmenityService).findHotelAmenity(hotelAmenity);
    }
}
