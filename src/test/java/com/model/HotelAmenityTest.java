package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelAmenityTest {

    private HotelAmenity hotelAmenity;

    @BeforeEach
    void setUp() {
        hotelAmenity = new HotelAmenity();
    }

    @Test
    void testGetAndSetHotel() {
        Hotel hotel = new Hotel(); // Assuming you have a Hotel class
        hotelAmenity.setHotel(hotel);
        assertEquals(hotel, hotelAmenity.getHotel());
    }

    @Test
    void testGetAndSetAmenity() {
        Amenity amenity = new Amenity(); // Assuming you have an Amenity class
        hotelAmenity.setAmenity(amenity);
        assertEquals(amenity, hotelAmenity.getAmenity());
    }
}
