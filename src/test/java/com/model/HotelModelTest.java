package com.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HotelModelTest {

    private Hotel hotel;
    private Area area;

    @BeforeEach
    void setUp() {
        // Initialize the objects before each test
        hotel = new Hotel();
        area = new Area();
    }

    @Test
    void testHotelIdGetterAndSetter() {
        long hotelId = 1L;
        hotel.setHotelId(hotelId);
        assertEquals(hotelId, hotel.getHotelId(), "Hotel ID should match the value set.");
    }

    @Test
    void testNameGetterAndSetter() {
        String name = "Grand Plaza";
        hotel.setName(name);
        assertEquals(name, hotel.getName(), "Hotel name should match the value set.");
    }

    @Test
    void testLocationGetterAndSetter() {
        String location = "New York";
        hotel.setLocation(location);
        assertEquals(location, hotel.getLocation(), "Hotel location should match the value set.");
    }

    @Test
    void testDescriptionGetterAndSetter() {
        String description = "A luxurious hotel in the heart of the city.";
        hotel.setDescription(description);
        assertEquals(description, hotel.getDescription(), "Hotel description should match the value set.");
    }

    @Test
    void testAreaGetterAndSetter() {
        // Initialize the area
        area.setName("Downtown");
        
        // Set the area to the hotel
        hotel.setArea(area);
        
        // Test if area is set correctly
        assertEquals(area, hotel.getArea(), "Hotel area should match the area set.");
        assertEquals("Downtown", hotel.getArea().getName(), "Area name should match the area set.");
    }

    @Test
    void testAreaWhenNoAreaSet() {
        // If no area is set, it should be null
        assertNull(hotel.getArea(), "Hotel area should be null if not set.");
    }

    @Test
    void testHotelCreation() {
        // Set all properties
        hotel.setHotelId(101L);
        hotel.setName("Ocean View");
        hotel.setLocation("California");
        hotel.setDescription("A beachfront hotel");

        // Verify all properties
        assertEquals(101L, hotel.getHotelId());
        assertEquals("Ocean View", hotel.getName());
        assertEquals("California", hotel.getLocation());
        assertEquals("A beachfront hotel", hotel.getDescription());
    }

    @Test
    void testAreaRelationship() {
        // Initialize an area
        area.setName("City Center");

        // Set the area for the hotel
        hotel.setArea(area);

        // Ensure that the area is set properly
        assertEquals(area, hotel.getArea());
        assertEquals("City Center", hotel.getArea().getName());
    }
}
