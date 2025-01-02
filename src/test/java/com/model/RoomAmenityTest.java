package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomAmenityTest {

    private RoomAmenity roomAmenity;

    @BeforeEach
    void setUp() {
        roomAmenity = new RoomAmenity();
    }

    @Test
    void testGetAndSetRoom() {
        Room room = new Room(); // Assuming you have a Room class
        roomAmenity.setRoom(room);
        assertEquals(room, roomAmenity.getRoom());
    }

    @Test
    void testGetAndSetAmenity() {
        Amenity amenity = new Amenity(); // Assuming you have an Amenity class
        roomAmenity.setAmenity(amenity);
        assertEquals(amenity, roomAmenity.getAmenity());
    }
}
