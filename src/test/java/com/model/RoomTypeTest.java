package com.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomTypeTest {

    private RoomType roomType;

    @BeforeEach
    void setUp() {
        roomType = new RoomType();
    }

    @Test
    void testSetAndGetRoomTypeId() {
        long roomTypeId = 1L;
        roomType.setRoomTypeId(roomTypeId);
        assertEquals(roomTypeId, roomType.getRoomTypeId(), "RoomType ID should be set and retrieved correctly");
    }

    @Test
    void testSetAndGetTypeName() {
        String typeName = "Deluxe";
        roomType.setTypeName(typeName);
        assertEquals(typeName, roomType.getTypeName(), "TypeName should be set and retrieved correctly");
    }

    @Test
    void testSetAndGetDescription() {
        String description = "Spacious room with ocean view";
        roomType.setDescription(description);
        assertEquals(description, roomType.getDescription(), "Description should be set and retrieved correctly");
    }

    @Test
    void testSetAndGetMaxOccupancy() {
        int maxOccupancy = 4;
        roomType.setMaxOccupancy(maxOccupancy);
        assertEquals(maxOccupancy, roomType.getMaxOccupancy(), "MaxOccupancy should be set and retrieved correctly");
    }

    @Test
    void testSetAndGetPricePerNight() {
        double pricePerNight = 150.75;
        roomType.setPricePerNight(pricePerNight);
        assertEquals(pricePerNight, roomType.getPricePerNight(), "PricePerNight should be set and retrieved correctly");
    }

    @Test
    void testRoomTypeInitialization() {
        assertNull(roomType.getTypeName(), "TypeName should be null initially");
        assertNull(roomType.getDescription(), "Description should be null initially");
        assertEquals(0, roomType.getMaxOccupancy(), "MaxOccupancy should be 0 initially");
        assertEquals(0.0, roomType.getPricePerNight(), "PricePerNight should be 0.0 initially");
    }
}

