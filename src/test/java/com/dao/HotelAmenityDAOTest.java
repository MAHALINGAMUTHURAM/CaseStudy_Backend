package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Amenity;
import com.model.Hotel;
import com.model.HotelAmenity;
import com.model.HotelAmenityId;
import com.service.HotelAmenityService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class HotelAmenityDAOTest {

    @Mock
    private HotelAmenityDAO hotelAmenityDAO;

    @InjectMocks
    private HotelAmenityService hotelAmenityService; // Assuming you have a service that uses the DAO

    private Hotel hotel;
    private Amenity amenity;
    private HotelAmenity hotelAmenity;

    private final long amenityId = 1L; // Dynamic value for amenity ID
    private final long hotelId = 2L; // Dynamic value for hotel ID
    private final HotelAmenityId hotelAmenityId = new HotelAmenityId(hotelId, amenityId); // Composite key

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Hotel and Amenity objects
        hotel = new Hotel();
        hotel.setHotelId(hotelId); // Assuming Hotel has an ID field
        
        amenity = new Amenity();
        amenity.setAmenityId(amenityId); // Assuming Amenity has an ID field

        // Initialize HotelAmenity object
        hotelAmenity = new HotelAmenity();
        hotelAmenity.setHotel(hotel);
        hotelAmenity.setAmenity(amenity);
       // Set composite key
    }

    @Test
    public void testSaveHotelAmenity() {
        // Given
        when(hotelAmenityDAO.save(any(HotelAmenity.class))).thenReturn(hotelAmenity);

        // When
        hotelAmenityService.saveHotelAmenity(hotelAmenity);

        // Then
        verify(hotelAmenityDAO, times(1)).save(hotelAmenity);
    }

    @Test
    public void testGetHotelsByAmenity() {
        // Given
        List<HotelAmenity> amenities = new ArrayList<>();
        amenities.add(hotelAmenity);
        
        when(hotelAmenityDAO.findByAmenity_AmenityId(amenityId)).thenReturn(amenities);

        // When
        List<Hotel> foundHotels = hotelAmenityService.getHotelsByAmenity(amenityId);

        // Then
        assertThat(foundHotels).isNotEmpty();
        assertThat(foundHotels.get(0)).isEqualTo(hotel);
        
        verify(hotelAmenityDAO, times(1)).findByAmenity_AmenityId(amenityId);
    }

    @Test
    public void testGetAmenitiesByHotel() {
        // Given
        List<HotelAmenity> amenities = new ArrayList<>();
        amenities.add(hotelAmenity);
        
        when(hotelAmenityDAO.findByHotel_HotelId(hotelId)).thenReturn(amenities);

        // When
        List<Amenity> foundAmenities = hotelAmenityService.getAmenitiesByHotel(hotelId);

        // Then
        assertThat(foundAmenities).isNotEmpty();
        assertThat(foundAmenities.get(0)).isEqualTo(amenity);
        
        verify(hotelAmenityDAO, times(1)).findByHotel_HotelId(hotelId);
    }

   
}
