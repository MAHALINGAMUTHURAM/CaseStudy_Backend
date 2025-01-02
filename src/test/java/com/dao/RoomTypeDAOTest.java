package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.RoomType;
import com.service.RoomTypeService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomTypeDAOTest {

    @Mock
    private RoomTypeDAO roomTypeDAO;

    @InjectMocks
    private RoomTypeService roomTypeService; // Assuming you have a service that uses the DAO

    private RoomType roomType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize RoomType object
        roomType = new RoomType();
        roomType.setRoomTypeId(1L); // Assuming RoomType has an ID field
        roomType.setTypeName("Deluxe Room"); // Set type name dynamically
        roomType.setDescription("A spacious deluxe room with a sea view.");
        roomType.setMaxOccupancy(2);
        roomType.setPricePerNight(150.00);
    }

    @Test
    public void testSaveRoomType() {
        // Given
        when(roomTypeDAO.save(any(RoomType.class))).thenReturn(roomType);

        // When
        RoomType savedRoomType = roomTypeDAO.save(roomType);

        // Then
        assertThat(savedRoomType).isNotNull();
        assertThat(savedRoomType.getRoomTypeId()).isEqualTo(roomType.getRoomTypeId());
        assertThat(savedRoomType.getTypeName()).isEqualTo(roomType.getTypeName());

        // Verify that the method was called once
        verify(roomTypeDAO, times(1)).save(any(RoomType.class));
    }

    @Test
    public void testFindById() {
        // Given
        when(roomTypeDAO.findById(roomType.getRoomTypeId())).thenReturn(Optional.of(roomType));

        // When
        Optional<RoomType> foundRoomType = roomTypeDAO.findById(roomType.getRoomTypeId());

        // Then
        assertThat(foundRoomType).isPresent();
        assertThat(foundRoomType.get().getDescription()).isEqualTo(roomType.getDescription());

        // Verify that the method was called once
        verify(roomTypeDAO, times(1)).findById(roomType.getRoomTypeId());
    }

    @Test
    public void testFindById_NoResults() {
        // Given
        when(roomTypeDAO.findById(roomType.getRoomTypeId())).thenReturn(Optional.empty());

        // When
        Optional<RoomType> foundRoomType = roomTypeDAO.findById(roomType.getRoomTypeId());

        // Then
        assertThat(foundRoomType).isNotPresent();

        // Verify that the method was called once
        verify(roomTypeDAO, times(1)).findById(roomType.getRoomTypeId());
    }

    @Test
    public void testFindAll() {
        // Given
        List<RoomType> roomTypes = new ArrayList<>();
        roomTypes.add(roomType);
        
        when(roomTypeDAO.findAll()).thenReturn(roomTypes);

        // When
        List<RoomType> foundRoomTypes = roomTypeDAO.findAll();

        // Then
        assertThat(foundRoomTypes).hasSize(1);
        assertThat(foundRoomTypes.get(0).getDescription()).isEqualTo(roomType.getDescription());

        // Verify that the method was called once
        verify(roomTypeDAO, times(1)).findAll();
    }

    @Test
    public void testDeleteRoomType() {
        // When
        roomTypeDAO.delete(roomType);

        // Then (no exception should be thrown)

        // Verify that the delete method was called once
        verify(roomTypeDAO, times(1)).delete(roomType);
    }
}
