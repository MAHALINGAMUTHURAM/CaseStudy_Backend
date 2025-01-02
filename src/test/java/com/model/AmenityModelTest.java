package com.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmenityModelTest {

    private Amenity amenity;

    @BeforeEach
    void setUp() {
        amenity = new Amenity();
    }

    @Test
    void testAmenityIdGetterAndSetter() {
        long amenityId = 1L;
        amenity.setAmenityId(amenityId);
        assertEquals(amenityId, amenity.getAmenityId());
    }

    @Test
    void testNameGetterAndSetter() {
        String name = "Swimming Pool";
        amenity.setName(name);
        assertEquals(name, amenity.getName());
    }

    @Test
    void testDescriptionGetterAndSetter() {
        String description = "A large pool for swimming and relaxation.";
        amenity.setDescription(description);
        assertEquals(description, amenity.getDescription());
    }
}
