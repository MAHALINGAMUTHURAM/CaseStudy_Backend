package com.model;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AreaModelTest {

    private Area area;

    @BeforeEach
    void setUp() {
        // Initialize a new Area object before each test
        area = new Area();
    }

    @Test
    void testAreaIdGetterAndSetter() {
        long areaId = 123L;
        area.setAreaId(areaId);
        assertEquals(areaId, area.getAreaId(), "Area ID should match the value set.");
    }

    @Test
    void testNameGetterAndSetter() {
        String name = "Downtown";
        area.setName(name);
        assertEquals(name, area.getName(), "Name should match the value set.");
    }
}
