package com.controller;


import com.controller.HotelController;
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
        Hotel hotel = new Hotel();
 
        when(hotelService.findByHotelLocation(hotel.getLocation())).thenReturn(true);
 
        ResponseEntity<Object> response = hotelController.addHotel(hotel);
 
        assertEquals(201, response.getStatusCodeValue());
        Response responseBody = (Response) response.getBody();
        assertNotNull(responseBody);
        assertEquals("POSTSUCCESS", responseBody.getCode());
        verify(hotelService, times(1)).saveHotel(hotel);
    }
 
    @Test
    void testAddHotel_Failure_HotelExists() {
        Hotel hotel = new Hotel();
 
        when(hotelService.findByHotelLocation(hotel.getLocation())).thenReturn(false);
 
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.addHotel(hotel);
        });
 
        assertEquals("ADDFAILS", exception.getCode());
        verify(hotelService, never()).saveHotel(hotel);
    }
 
    @Test
    void testGetAllHotels_Success() throws CustomException {
        List<Hotel> hotels = Arrays.asList(
                new Hotel(),
                new Hotel()
        );
 
        when(hotelService.getAllHotels()).thenReturn(hotels);
 
        ResponseEntity<Object> response = hotelController.getAllHotels();
 
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(((List<?>) response.getBody()).containsAll(hotels));
        verify(hotelService, times(1)).getAllHotels();
    }
 
    @Test
    void testGetAllHotels_Failure_EmptyList() {
        when(hotelService.getAllHotels()).thenReturn(Arrays.asList());
 
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.getAllHotels();
        });
 
        assertEquals("GETFAILS", exception.getCode());
    }
 
    @Test
    void testGetHotelById_Success() throws CustomException {
        Hotel hotel = new Hotel();
 
        when(hotelService.existsById(1L)).thenReturn(true);
        when(hotelService.getHotelById(1L)).thenReturn(hotel);
 
        ResponseEntity<Object> response = hotelController.getHotelById(1L);
 
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(hotel, response.getBody());
        verify(hotelService, times(1)).existsById(1L);
        verify(hotelService, times(1)).getHotelById(1L);
    }
 
    @Test
    void testGetHotelById_Failure_NotFound() {
        when(hotelService.existsById(1L)).thenReturn(false);
 
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.getHotelById(1L);
        });
 
        assertEquals("GETFAILS", exception.getCode());
        verify(hotelService, times(1)).existsById(1L);
        verify(hotelService, never()).getHotelById(1L);
    }
 
    @Test
    void testGetHotelsByAmenity_Success() throws CustomException {
        List<Hotel> hotels = Arrays.asList(
                new Hotel(),
                new Hotel()
        );
 
        when(hotelAmenityService.getHotelsByAmenity(1L)).thenReturn(hotels);
 
        ResponseEntity<Object> response = hotelController.getHotelsByAmenity(1L);
 
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(((List<?>) response.getBody()).containsAll(hotels));
        verify(hotelAmenityService, times(1)).getHotelsByAmenity(1L);
    }
 
    @Test
    void testGetHotelsByAmenity_Failure_EmptyList() {
        when(hotelAmenityService.getHotelsByAmenity(1L)).thenReturn(Arrays.asList());
 
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.getHotelsByAmenity(1L);
        });
 
        assertEquals("GETFAILS", exception.getCode());
        verify(hotelAmenityService, times(1)).getHotelsByAmenity(1L);
    }
 
    @Test
    void testUpdateHotel_Success() throws CustomException {
        Hotel updatedHotel = new Hotel();
 
        when(hotelService.existsById(1L)).thenReturn(true);
 
        ResponseEntity<Object> response = hotelController.updateHotel(1L, updatedHotel);
 
        assertEquals(200, response.getStatusCodeValue());
        Response responseBody = (Response) response.getBody();
        assertNotNull(responseBody);
        assertEquals("UPDATESUCCESS", responseBody.getCode());
        verify(hotelService, times(1)).updateHotel(1L, updatedHotel);
    }
 
    @Test
    void testUpdateHotel_Failure_NotFound() {
        Hotel updatedHotel = new Hotel();
 
        when(hotelService.existsById(1L)).thenReturn(false);
 
        CustomException exception = assertThrows(CustomException.class, () -> {
            hotelController.updateHotel(1L, updatedHotel);
        });
 
        assertEquals("UPDTFAILS", exception.getCode());
        verify(hotelService, never()).updateHotel(1L, updatedHotel);
    }
}
 
