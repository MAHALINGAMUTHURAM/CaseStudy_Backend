package com.controller;

import com.model.Hotel;
import com.service.HotelService;
import com.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
public class HotelControllerTest {

    private MockMvc mockMvc;

    @Mock
    private HotelService hotelService;

    @InjectMocks
    private HotelController hotelController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(hotelController).build();
    }

    @Test
    void testAddHotel_Success() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel ABC");
        hotel.setLocation("Location XYZ");
        hotel.setDescription("A description of Hotel ABC");

        when(hotelService.findByHotelLocation(hotel.getLocation())).thenReturn(false); // Simulate hotel doesn't exist
        doNothing().when(hotelService).saveHotel(hotel); // Use doNothing() for void methods

        mockMvc.perform(post("/api/hotels/post")
                .contentType("application/json")
                .content("{\"name\":\"Hotel ABC\",\"location\":\"Location XYZ\",\"description\":\"A description of Hotel ABC\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Hotel added successfully"))
                .andReturn();

        verify(hotelService, times(1)).saveHotel(hotel);
    }

    @Test
    void testAddHotel_Failure_HotelAlreadyExists() throws Exception {
        Hotel hotel = new Hotel();
        hotel.setName("Hotel ABC");
        hotel.setLocation("Location XYZ");
        hotel.setDescription("A description of Hotel ABC");

        when(hotelService.findByHotelLocation(hotel.getLocation())).thenReturn(true); // Simulate hotel exists

        mockMvc.perform(post("/api/hotels/post")
                .contentType("application/json")
                .content("{\"name\":\"Hotel ABC\",\"location\":\"Location XYZ\",\"description\":\"A description of Hotel ABC\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Hotel already exists"))
                .andReturn();

        verify(hotelService, never()).saveHotel(hotel); // Hotel should not be saved if it already exists
    }

    @Test
    void testUpdateHotel_Success() throws Exception {
        Long hotelId = 1L;
        Hotel hotel = new Hotel();
        hotel.setName("Updated Hotel");
        hotel.setLocation("Location XYZ");
        hotel.setDescription("Updated description");

        when(hotelService.existsById(hotelId)).thenReturn(true); // Simulate hotel exists
        doNothing().when(hotelService).updateHotel(eq(hotelId), any(Hotel.class)); // Use any() for Hotel object

        mockMvc.perform(put("/api/hotels/update/{hotelId}", hotelId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Hotel\",\"location\":\"Location XYZ\",\"description\":\"Updated description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hotel updated successfully"))
                .andReturn();

        // Verify that updateHotel was called with the correct arguments
        verify(hotelService, times(1)).updateHotel(eq(hotelId), any(Hotel.class));
    }

    @Test
    void testDeleteHotel_Success() throws Exception {
        Long hotelId = 1L;

        when(hotelService.existsById(hotelId)).thenReturn(true);
        doNothing().when(hotelService).deleteHotel(hotelId);

        mockMvc.perform(delete("/api/hotels/delete/{hotelId}", hotelId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("RoomType deleted successfully"))
                .andReturn();

        verify(hotelService, times(1)).deleteHotel(hotelId);
    }

    @Test
    void testDeleteHotel_Failure_NotFound() throws Exception {
        Long hotelId = 1L;

        when(hotelService.existsById(hotelId)).thenReturn(false);

        mockMvc.perform(delete("/api/hotels/delete/{hotelId}", hotelId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("RoomType doesn't exist"))
                .andReturn();

        verify(hotelService, never()).deleteHotel(hotelId);
    }
}
