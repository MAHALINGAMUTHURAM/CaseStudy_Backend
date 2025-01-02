package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Hotel;
import com.service.HotelService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class HotelDAOTest {

    @Mock
    private HotelDAO hotelDAO;

    @InjectMocks
    private HotelService hotelService; // Assuming you have a service that uses the DAO

    private Hotel hotel;
    private final String location = "Downtown"; // Dynamic value for location
    private final long areaId = 1L; // Dynamic value for area ID

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Hotel object
        hotel = new Hotel();
        hotel.setHotelId(1L); // Assuming Hotel has an ID field
        hotel.setLocation(location); // Set location dynamically
    }

    @Test
    public void testFindByLocation() {
        // Given
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        
        when(hotelDAO.findByLocation(location)).thenReturn(hotels);

        // When
        List<Hotel> foundHotels = hotelDAO.findByLocation(location);

        // Then
        assertThat(foundHotels).isNotEmpty();
        assertThat(foundHotels.get(0).getLocation()).isEqualTo(location);

        // Verify that the method was called once
        verify(hotelDAO, times(1)).findByLocation(location);
    }

    @Test
    public void testFindByLocation_NoResults() {
        // Given
        when(hotelDAO.findByLocation(location)).thenReturn(new ArrayList<>());

        // When
        List<Hotel> foundHotels = hotelDAO.findByLocation(location);

        // Then
        assertThat(foundHotels).isEmpty();

        // Verify that the method was called once
        verify(hotelDAO, times(1)).findByLocation(location);
    }

    @Test
    public void testFindByArea_AreaId() {
        // Given
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        
        when(hotelDAO.findByArea_AreaId(areaId)).thenReturn(hotels);

        // When
        List<Hotel> foundHotels = hotelDAO.findByArea_AreaId(areaId);

        // Then
        assertThat(foundHotels).isNotEmpty();
        assertThat(foundHotels.get(0).getHotelId()).isEqualTo(hotel.getHotelId());

        // Verify that the method was called once
        verify(hotelDAO, times(1)).findByArea_AreaId(areaId);
    }

    @Test
    public void testFindByArea_AreaId_NoResults() {
        // Given
        when(hotelDAO.findByArea_AreaId(areaId)).thenReturn(new ArrayList<>());

        // When
        List<Hotel> foundHotels = hotelDAO.findByArea_AreaId(areaId);

        // Then
        assertThat(foundHotels).isEmpty();

        // Verify that the method was called once
        verify(hotelDAO, times(1)).findByArea_AreaId(areaId);
    }
}
