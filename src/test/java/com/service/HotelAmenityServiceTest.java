package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dao.HotelAmenityDAO;
import com.model.Amenity;
import com.model.Hotel;
import com.model.HotelAmenity;
import com.service.HotelAmenityService;

class HotelAmenityServiceTest {

    @InjectMocks
    private HotelAmenityService hotelAmenityService;

    @Mock
    private HotelAmenityDAO hotelAmenityDAO;

    private Hotel hotel1, hotel2;
    private Amenity amenity1, amenity2;
    private HotelAmenity hotelAmenity1, hotelAmenity2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize Hotel objects
        hotel1 = new Hotel();
        hotel1.setHotelId(1L);
        hotel1.setName("Hotel A");

        hotel2 = new Hotel();
        hotel2.setHotelId(2L);
        hotel2.setName("Hotel B");

        // Initialize Amenity objects
        amenity1 = new Amenity();
        amenity1.setAmenityId(1L);
        amenity1.setName("Pool");

        amenity2 = new Amenity();
        amenity2.setAmenityId(2L);
        amenity2.setName("Gym");

        // Initialize HotelAmenity objects
        hotelAmenity1 = new HotelAmenity();
        hotelAmenity1.setHotel(hotel1);
        hotelAmenity1.setAmenity(amenity1);

        hotelAmenity2 = new HotelAmenity();
        hotelAmenity2.setHotel(hotel2);
        hotelAmenity2.setAmenity(amenity2);
    }

    @Test
    void testSaveHotelAmenity() {
        // Save a HotelAmenity and check if it was added to the list
        hotelAmenityService.saveHotelAmenity(hotelAmenity1);

        assertTrue(hotelAmenityService.findHotelAmenity(hotelAmenity1));
        verify(hotelAmenityDAO, times(1)).save(hotelAmenity1);
    }

    @Test
    void testGetHotelsByAmenity() {
        // Mock the DAO to return a list of HotelAmenity objects for a given amenity ID
        when(hotelAmenityDAO.findByAmenity_AmenityId(1L))
                .thenReturn(Collections.singletonList(hotelAmenity1));

        // Call the service method and verify results
        List<Hotel> hotels = hotelAmenityService.getHotelsByAmenity(1L);

        assertEquals(1, hotels.size()); // Verifying size
        assertEquals(1L, hotels.get(0).getHotelId()); // Verifying hotel ID
        // OR assertEquals("Hotel A", hotels.get(0).getHotelName()); // Verifying hotel name
        verify(hotelAmenityDAO, times(1)).findByAmenity_AmenityId(1L); // Mock verification
    }

    @Test
    void testGetAmenitiesByHotel() {
        // Mock the DAO to return a list of HotelAmenity objects for a given hotel ID
        when(hotelAmenityDAO.findByHotel_HotelId(1L))
                .thenReturn(Collections.singletonList(hotelAmenity1));

        // Call the service method and verify results
        List<Amenity> amenities = hotelAmenityService.getAmenitiesByHotel(1L);

        assertEquals(1, amenities.size());
        assertEquals(1L, amenities.get(0).getAmenityId()); // Fix: Comparing ID
        verify(hotelAmenityDAO, times(1)).findByHotel_HotelId(1L);
    }


    @Test
    void testFindHotelAmenity() {
        // Save a HotelAmenity and check if it can be found
        hotelAmenityService.saveHotelAmenity(hotelAmenity2);

        boolean exists = hotelAmenityService.findHotelAmenity(hotelAmenity2);
        assertTrue(exists);

        boolean notExists = hotelAmenityService.findHotelAmenity(hotelAmenity1);
        assertFalse(notExists);
    }
}
