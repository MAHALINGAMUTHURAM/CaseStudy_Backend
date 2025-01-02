package com.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
    }

    @Test
    void testGetAndSetRoomId() {
        long expectedId = 1L;
        room.setRoomId(expectedId);
        assertEquals(expectedId, room.getRoomId());
    }

    @Test
    void testGetAndSetRoomNumber() {
        int expectedRoomNumber = 101;
        room.setRoomNumber(expectedRoomNumber);
        assertEquals(expectedRoomNumber, room.getRoomNumber());
    }

    @Test
    void testGetAndSetLocation() {
        String expectedLocation = "First Floor";
        room.setLocation(expectedLocation);
        assertEquals(expectedLocation, room.getLocation());
    }

    @Test
    void testGetAndSetAvailable() {
        boolean expectedAvailability = true;
        room.setAvailable(expectedAvailability);
        assertEquals(expectedAvailability, room.isAvailable());
    }

    @Test
    void testGetAndSetRoomType() {
        RoomType roomType = new RoomType(); // Assuming you have a RoomType class
        room.setRoomtype(roomType);
        assertEquals(roomType, room.getRoomtype());
    }

    @Test
    void testGetAndSetHotel() {
        Hotel hotel = new Hotel(); // Assuming you have a Hotel class
        room.setHotel(hotel);
        assertEquals(hotel, room.getHotel());
    }
}
