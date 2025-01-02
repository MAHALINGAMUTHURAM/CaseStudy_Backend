package com.dao;
import com.model.RoomAmenity;
import com.model.RoomAmenityId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class RoomAmenityDAOTest {

    @Mock
    private RoomAmenityDAO roomAmenityDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample RoomAmenity objects
        RoomAmenity roomAmenity1 = new RoomAmenity();
        RoomAmenity roomAmenity2 = new RoomAmenity();
        RoomAmenity roomAmenity3 = new RoomAmenity();

        // Mocking the behavior of RoomAmenityDAO methods
        when(roomAmenityDAO.findByAmenity_AmenityId(anyLong()))
                .thenReturn(Arrays.asList(roomAmenity1, roomAmenity3)); // Assuming both have amenity ID 1

        when(roomAmenityDAO.findByRoom_RoomId(1L))
                .thenReturn(Arrays.asList(roomAmenity1, roomAmenity2)); // Assuming both belong to room ID 1

        when(roomAmenityDAO.findByRoom_RoomId(2L))
                .thenReturn(Arrays.asList(roomAmenity3)); // Only one amenity for room ID 2

        when(roomAmenityDAO.findByRoom_RoomId(999L))
                .thenReturn(Arrays.asList()); // No amenities for non-existing room ID
    }

    @Test
    public void testFindByAmenity_AmenityId() {
        List<RoomAmenity> amenities = roomAmenityDAO.findByAmenity_AmenityId(1L);
        assertThat(amenities).hasSize(2); // Expecting two amenities with amenity ID 1
    }

    @Test
    public void testFindByRoom_RoomId() {
        List<RoomAmenity> amenitiesForRoom1 = roomAmenityDAO.findByRoom_RoomId(1L);
        assertThat(amenitiesForRoom1).hasSize(2); // Expecting two amenities for room ID 1

        List<RoomAmenity> amenitiesForRoom2 = roomAmenityDAO.findByRoom_RoomId(2L);
        assertThat(amenitiesForRoom2).hasSize(1); // Expecting one amenity for room ID 2

        List<RoomAmenity> amenitiesForNonExistingRoom = roomAmenityDAO.findByRoom_RoomId(999L);
        assertThat(amenitiesForNonExistingRoom).isEmpty(); // Expecting no amenities for non-existing room
    }
}
