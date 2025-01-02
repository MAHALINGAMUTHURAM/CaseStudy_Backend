package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HotelAmenityIdTest {

    private HotelAmenityId hotelAmenityId1;
    private HotelAmenityId hotelAmenityId2;
    private HotelAmenityId hotelAmenityId3;

    @BeforeEach
    public void setUp() {
        // Creating instances for testing
        hotelAmenityId1 = new HotelAmenityId(1L, 101L);
        hotelAmenityId2 = new HotelAmenityId(1L, 101L); // Same hotel and amenity as hotelAmenityId1
        hotelAmenityId3 = new HotelAmenityId(2L, 102L); // Different hotel and amenity
    }

    @Test
    public void testEquals() {
        // Test for equality with the same values
        assertTrue(hotelAmenityId1.equals(hotelAmenityId2), "hotelAmenityId1 should be equal to hotelAmenityId2");

        // Test for inequality with different values
        assertFalse(hotelAmenityId1.equals(hotelAmenityId3), "hotelAmenityId1 should not be equal to hotelAmenityId3");

        // Test for equality with itself (reflexive property)
        assertTrue(hotelAmenityId1.equals(hotelAmenityId1), "hotelAmenityId1 should be equal to itself");
        
        // Test for null (should not be equal to null)
        assertFalse(hotelAmenityId1.equals(null), "hotelAmenityId1 should not be equal to null");
        
        // Test for different class type (should not be equal)
        assertFalse(hotelAmenityId1.equals("some string"), "hotelAmenityId1 should not be equal to a string");
    }

    @Test
    public void testHashCode() {
        // Test hashCode consistency
        assertEquals(hotelAmenityId1.hashCode(), hotelAmenityId2.hashCode(), "hashCode should be the same for equal objects");
        
        // Test hashCode for different objects
        assertNotEquals(hotelAmenityId1.hashCode(), hotelAmenityId3.hashCode(), "hashCode should be different for unequal objects");
    }

    @Test
    public void testGettersAndSetters() {
        // Test the getter and setter methods
        hotelAmenityId1.setHotel(3L);
        hotelAmenityId1.setAmenity(201L);

        assertEquals(3L, hotelAmenityId1.getHotel(), "Hotel ID should be set correctly");
        assertEquals(201L, hotelAmenityId1.getAmenity(), "Amenity ID should be set correctly");
    }
}
