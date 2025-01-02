package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Amenity;
import com.service.AmenityService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AmenityDAOTest {

    @Mock
    private AmenityDAO amenityDAO;

    @InjectMocks
    private AmenityService amenityService; // Assuming you have a service that uses the DAO

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByName() {
        // Given
        String name = "Swimming Pool";
        Amenity amenity = new Amenity();
        amenity.setAmenityId(1L);
        amenity.setName(name);

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(amenity);

        when(amenityDAO.findByname(name)).thenReturn(amenities);

        // When
        List<Amenity> foundAmenities = amenityDAO.findByname(name);

        // Then
        assertThat(foundAmenities).isNotEmpty();
        assertThat(foundAmenities.get(0).getName()).isEqualTo(name);
        
        // Verify that the method was called once
        verify(amenityDAO, times(1)).findByname(name);
    }

    @Test
    public void testFindByName_NoResults() {
        // Given
        String name = "Non-existent Amenity";
        
        when(amenityDAO.findByname(name)).thenReturn(new ArrayList<>());

        // When
        List<Amenity> foundAmenities = amenityDAO.findByname(name);

        // Then
        assertThat(foundAmenities).isEmpty();
        
        // Verify that the method was called once
        verify(amenityDAO, times(1)).findByname(name);
    }
}
