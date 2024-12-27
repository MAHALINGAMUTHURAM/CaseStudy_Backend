package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dao.AmenityDAO;
import com.model.Amenity;
import com.service.AmenityService;

class AmenityServiceTest {

    @InjectMocks
    private AmenityService amenityService;

    @Mock
    private AmenityDAO amenityDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAmenities() {
        List<Amenity> mockAmenities = Arrays.asList(
            new Amenity(),
            new Amenity()
        );

        when(amenityDAO.findAll()).thenReturn(mockAmenities);

        List<Amenity> amenities = amenityService.getAllAmenities();
        assertEquals(2, amenities.size());
        verify(amenityDAO, times(1)).findAll();
    }

    @Test
    void testSaveAmenity() {
        Amenity amenity = new Amenity();
        amenityService.saveAmenity(amenity);
        verify(amenityDAO, times(1)).save(amenity);
    }

    @Test
    void testUpdateAmenity() {
        // Arrange: Set up existing and updated Amenity objects
        Amenity existingAmenity = new Amenity();
        existingAmenity.setName("OldAmenity");
        existingAmenity.setDescription("OldDescription");

        Amenity updatedAmenity = new Amenity();
        updatedAmenity.setName("UpdatedAmenity");
        updatedAmenity.setDescription("UpdatedDescription");

        // Mock the DAO to return the existing Amenity
        when(amenityDAO.findById(1L)).thenReturn(Optional.of(existingAmenity));

        // Act: Call the updateAmenity method
        amenityService.updateAmenity(1L, updatedAmenity);

        // Assert: Verify that the existing Amenity has been updated and saved
        verify(amenityDAO, times(1)).save(existingAmenity);
        assertEquals("UpdatedAmenity", existingAmenity.getName());
        assertEquals("UpdatedDescription", existingAmenity.getDescription());
    }


    @Test
    void testDeleteAmenity() {
        Amenity amenity = new Amenity();
        when(amenityDAO.findById(1L)).thenReturn(Optional.of(amenity));

        amenityService.deleteAmenity(1L);

        verify(amenityDAO, times(1)).delete(amenity);
    }

    @Test
    void testGetAmenityById() {
        // Arrange: Create and initialize the Amenity object
        Amenity amenity = new Amenity();
        amenity.setName("Amenity1"); // Set the name to match the expected value

        // Mock the DAO to return the prepared Amenity
        when(amenityDAO.findById(1L)).thenReturn(Optional.of(amenity));

        // Act: Call the service method
        Amenity result = amenityService.getAmenityById(1L);

        // Assert: Verify the result and interaction with the DAO
        assertEquals("Amenity1", result.getName());
        verify(amenityDAO, times(1)).findById(1L);
    }


    @Test
    void testExistsById() {
        when(amenityDAO.existsById(1L)).thenReturn(true);

        assertTrue(amenityService.existsById(1L));
        verify(amenityDAO, times(1)).existsById(1L);
    }

    @Test
    void testFindByAmenityName() {
        // Mocking the DAO method to return a list of Amenity objects
        when(amenityDAO.findByname("Amenity1")).thenReturn(Arrays.asList(new Amenity()));

        // Call the service method and store the result
        List<Amenity> amenities = amenityService.findByAmenityname("Amenity1");

        // Assert that the result is not empty (i.e., the list contains at least one Amenity)
        assertFalse(amenities.isEmpty());

        // Verify that the DAO method was called exactly once with the correct parameter
        verify(amenityDAO, times(1)).findByname("Amenity1");
    }
}