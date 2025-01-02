package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Area;
import com.service.AreaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AreaDAOTest {

    @Mock
    private AreaDAO areaDAO;

    @InjectMocks
    private AreaService areaService; // Assuming you have a service that uses the DAO

    private Area area;
    private final String areaName = "Downtown"; // Dynamic value for area name
    private final String anotherAreaName = "Uptown"; // Dynamic value for another area name
    private final Integer areaId = 1; // Dynamic value for area ID

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        area = new Area();
        area.setAreaId(areaId); // Set ID dynamically
        area.setName(areaName); // Set name dynamically
    }



    @Test
    public void testFindAll() {
        // Given
        List<Area> areas = new ArrayList<>();
        
        Area anotherArea = new Area();
        anotherArea.setAreaId(2); // Set ID dynamically for another area
        anotherArea.setName(anotherAreaName); // Use dynamic name
        
        areas.add(area);
        areas.add(anotherArea);
        
        when(areaDAO.findAll()).thenReturn(areas);

        // When
        List<Area> foundAreas = areaDAO.findAll();

        // Then
        assertThat(foundAreas).hasSize(2);
        assertThat(foundAreas).extracting(Area::getName)
            .containsExactlyInAnyOrder(area.getName(), anotherArea.getName());

        // Verify that the findAll method was called once
        verify(areaDAO, times(1)).findAll();
    }
}
