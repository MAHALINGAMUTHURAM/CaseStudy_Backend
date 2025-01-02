package com.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomAmenityIDTest {

    private RoomAmenityId roomAmenityId;
    private RoomAmenityId anotherRoomAmenityId;

    @BeforeEach
    void setUp() {
        roomAmenityId = new RoomAmenityId(1L, 2L);
        anotherRoomAmenityId = new RoomAmenityId(1L, 2L);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals(1L, roomAmenityId.getRoom(), "Room ID should be set and retrieved correctly");
        assertEquals(2L, roomAmenityId.getAmenity(), "Amenity ID should be set and retrieved correctly");
    }

    @Test
    void testSetters() {
        roomAmenityId.setRoom(3L);
        roomAmenityId.setAmenity(4L);

        assertEquals(3L, roomAmenityId.getRoom(), "Room ID should be updated correctly");
        assertEquals(4L, roomAmenityId.getAmenity(), "Amenity ID should be updated correctly");
    }

    @Test
    void testEqualsSameObject() {
        assertTrue(roomAmenityId.equals(roomAmenityId), "Same object should be equal");
    }

    @Test
    void testEqualsDifferentObjectSameValues() {
        assertTrue(roomAmenityId.equals(anotherRoomAmenityId), "Different objects with the same values should be equal");
    }

    @Test
    void testEqualsDifferentObjectDifferentValues() {
        RoomAmenityId differentRoomAmenityId = new RoomAmenityId(3L, 4L);
        assertFalse(roomAmenityId.equals(differentRoomAmenityId), "Objects with different values should not be equal");
    }

    @Test
    void testEqualsNullObject() {
        assertFalse(roomAmenityId.equals(null), "Object compared to null should not be equal");
    }

    @Test
    void testEqualsDifferentClass() {
        assertFalse(roomAmenityId.equals("SomeString"), "Object of different class should not be equal");
    }

    @Test
    void testHashCodeSameValues() {
        assertEquals(roomAmenityId.hashCode(), anotherRoomAmenityId.hashCode(), "Hash codes for equal objects should be the same");
    }

    @Test
    void testHashCodeDifferentValues() {
        RoomAmenityId differentRoomAmenityId = new RoomAmenityId(3L, 4L);
        assertNotEquals(roomAmenityId.hashCode(), differentRoomAmenityId.hashCode(), "Hash codes for different objects should not be the same");
    }
}

