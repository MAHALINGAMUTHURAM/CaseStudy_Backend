package com.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dao.HotelDAO;
import com.model.Hotel;
import com.service.HotelService;

class HotelServiceTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelDAO hotelDAO;

    private Hotel hotel1;
    private Hotel hotel2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hotel1 = new Hotel();
        hotel1.setHotelId(1L);
        hotel1.setName("Hotel A");
        hotel1.setLocation("City A");
        hotel1.setDescription("Luxury Hotel");

        hotel2 = new Hotel();
        hotel2.setHotelId(2L);
        hotel2.setName("Hotel B");
        hotel2.setLocation("City B");
        hotel2.setDescription("Budget Hotel");
    }

    @Test
    void testGetAllHotels() {
        // Mock the DAO to return a list of hotels
        when(hotelDAO.findAll()).thenReturn(Arrays.asList(hotel1, hotel2));

        // Call the service method
        List<Hotel> hotels = hotelService.getAllHotels();

        // Verify results
        assertEquals(2, hotels.size());
        assertEquals("Hotel A", hotels.get(0).getName());
        verify(hotelDAO, times(1)).findAll();
    }

    @Test
    void testSaveHotel() {
        // Call the service method
        hotelService.saveHotel(hotel1);

        // Verify that the DAO save method is called
        verify(hotelDAO, times(1)).save(hotel1);
    }

    @Test
    void testGetHotelById() {
        // Mock the DAO to return a hotel for a given ID
        when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));

        // Call the service method
        Hotel hotel = hotelService.getHotelById(1L);

        // Verify results
        assertNotNull(hotel);
        assertEquals("Hotel A", hotel.getName());
        verify(hotelDAO, times(1)).findById(1L);
    }

    @Test
    void testExistsById() {
        // Mock the DAO to return true for a given ID
        when(hotelDAO.existsById(1L)).thenReturn(true);

        // Call the service method
        boolean exists = hotelService.existsById(1L);

        // Verify results
        assertTrue(exists);
        verify(hotelDAO, times(1)).existsById(1L);
    }

    @Test
    void testDeleteHotel() {
        // Mock the DAO to return a hotel for a given ID
        when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));

        // Call the service method
        hotelService.deleteHotel(1L);

        // Verify that the DAO delete method is called
        verify(hotelDAO, times(1)).delete(hotel1);
    }

    @Test
    void testUpdateHotel() {
        // Mock the DAO to return an existing hotel
        when(hotelDAO.findById(1L)).thenReturn(Optional.of(hotel1));

        // Create a new hotel object with updated details
        Hotel updatedHotel = new Hotel();
        updatedHotel.setName("Updated Hotel A");
        updatedHotel.setLocation("Updated City A");
        updatedHotel.setDescription("Updated Luxury Hotel");

        // Call the service method
        hotelService.updateHotel(1L, updatedHotel);

        // Verify that the DAO save method is called
        verify(hotelDAO, times(1)).save(hotel1);

        // Verify the updated values
        assertEquals("Updated Hotel A", hotel1.getName());
        assertEquals("Updated City A", hotel1.getLocation());
        assertEquals("Updated Luxury Hotel", hotel1.getDescription());
    }

    @Test
    void testFindByHotelLocation() {
        // Mock the DAO to return an empty list for a location
        when(hotelDAO.findByLocation("Non-Existent City")).thenReturn(Arrays.asList());

        // Call the service method
        boolean exists = hotelService.findByHotelLocation("Non-Existent City");

        // Verify results
        assertFalse(exists);
        verify(hotelDAO, times(1)).findByLocation("Non-Existent City");

        // Mock the DAO to return a list of hotels for a location
        when(hotelDAO.findByLocation("City A")).thenReturn(Arrays.asList(hotel1));

        // Call the service method
        exists = hotelService.findByHotelLocation("City A");

        // Verify results
        assertTrue(exists);
        verify(hotelDAO, times(1)).findByLocation("City A");
    }
}
