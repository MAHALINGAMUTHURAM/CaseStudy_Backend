package com.controller;
 
import com.exception.CustomException;
import com.exception.Response;
import com.model.Hotel;
import com.service.HotelAmenityService;
import com.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
 
import java.util.Arrays;
import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
 
class HotelControllerTest {
 
    @InjectMocks
    private HotelController hotelController;
 
    @Mock
    private HotelService hotelService;
 
    @Mock
    private HotelAmenityService hotelAmenityService;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    @Test
    void testAddHotel_Success() throws CustomException {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setLocation("TestLocation"); // Initialize the location field
 
        when(hotelService.findByHotelLocation("TestLocation")).thenReturn(false);  // Hotel doesn't exist
 
        // Act
        ResponseEntity<Object> response = hotelController.addHotel(hotel);
 
        // Assert
        assertEquals(201, response.getStatusCodeValue());
        Response responseBody = (Response) response.getBody();
        assertNotNull(responseBody);
        assertEquals("POSTSUCCESS", responseBody.getCode());
        verify(hotelService, times(1)).saveHotel(hotel);
    }
 
    @Test
    void testAddHotel_Failure_HotelExists() {
        // Arrange
        Hotel hotel = new Hotel();
        hotel.setLocation("ExistingLocation");
 
        when(hotelService.findByHotelLocation("ExistingLocation")).thenReturn(true);  // Hotel exists
 
        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.addHotel(hotel);
        });
        assertEquals("ADDFAILS", exception.getCode());
        verify(hotelService, never()).saveHotel(hotel);
    }
 
    @Test
    void testGetAllHotels_Success() throws CustomException {
        // Arrange
        List<Hotel> hotels = Arrays.asList(new Hotel(), new Hotel());
        when(hotelService.getAllHotels()).thenReturn(hotels);
 
        // Act
        ResponseEntity<Object> response = hotelController.getAllHotels();
 
        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(((List<?>) response.getBody()).containsAll(hotels));
        verify(hotelService, times(1)).getAllHotels();
    }
 
    @Test
    void testGetAllHotels_Failure_EmptyList() {
        // Arrange
        when(hotelService.getAllHotels()).thenReturn(Arrays.asList()); // Empty list
 
        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.getAllHotels();
        });
 
        assertEquals("GETFAILS", exception.getCode());
    }
 
    @Test
    void testGetHotelById_Success() throws CustomException {
        // Arrange
        Hotel hotel = new Hotel();
        when(hotelService.existsById(1L)).thenReturn(true);
        when(hotelService.getHotelById(1L)).thenReturn(hotel);
 
        // Act
        ResponseEntity<Object> response = hotelController.getHotelById(1L);
 
        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(hotel, response.getBody());
        verify(hotelService, times(1)).existsById(1L);
        verify(hotelService, times(1)).getHotelById(1L);
    }
 
    @Test
    void testGetHotelById_Failure_NotFound() {
        // Arrange
        when(hotelService.existsById(1L)).thenReturn(false);
 
        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.getHotelById(1L);
        });
 
        assertEquals("GETFAILS", exception.getCode());
        verify(hotelService, times(1)).existsById(1L);
        verify(hotelService, never()).getHotelById(1L);
    }
 
    @Test
    void testGetHotelsByAmenity_Success() throws CustomException {
        // Arrange
        List<Hotel> hotels = Arrays.asList(new Hotel(), new Hotel());
        when(hotelAmenityService.getHotelsByAmenity(1L)).thenReturn(hotels);
 
        // Act
        ResponseEntity<Object> response = hotelController.getHotelsByAmenity(1L);
 
        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(((List<?>) response.getBody()).containsAll(hotels));
        verify(hotelAmenityService, times(1)).getHotelsByAmenity(1L);
    }
 
    @Test
    void testGetHotelsByAmenity_Failure_EmptyList() {
        // Arrange
        when(hotelAmenityService.getHotelsByAmenity(1L)).thenReturn(Arrays.asList()); // Empty list
 
        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.getHotelsByAmenity(1L);
        });
 
        assertEquals("GETFAILS", exception.getCode());
        verify(hotelAmenityService, times(1)).getHotelsByAmenity(1L);
    }
 
    @Test
    void testUpdateHotel_Success() throws CustomException {
        // Arrange
        Hotel updatedHotel = new Hotel();
        when(hotelService.existsById(1L)).thenReturn(true);
 
        // Act
        ResponseEntity<Object> response = hotelController.updateHotel(1L, updatedHotel);
 
        // Assert
        assertEquals(200, response.getStatusCodeValue());
        Response responseBody = (Response) response.getBody();
        assertNotNull(responseBody);
        assertEquals("UPDATESUCCESS", responseBody.getCode());
        verify(hotelService, times(1)).updateHotel(1L, updatedHotel);
    }
 
    @Test
    void testUpdateHotel_Failure_NotFound() {
        // Arrange
        Hotel updatedHotel = new Hotel();
        when(hotelService.existsById(1L)).thenReturn(false);
 
        // Act & Assert
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.updateHotel(1L, updatedHotel);
        });
 
        assertEquals("UPDTFAILS", exception.getCode());
        verify(hotelService, never()).updateHotel(1L, updatedHotel);
    }
}