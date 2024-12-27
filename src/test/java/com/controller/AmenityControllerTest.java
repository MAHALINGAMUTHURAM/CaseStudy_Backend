package com.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
import java.util.Arrays;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.controller.AmenityController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Amenity;
import com.service.AmenityService;
 
class AmenityControllerTest {
 
    @InjectMocks
    private AmenityController amenityController;
 
    @Mock
    private AmenityService amenityService;
 
    @Autowired
    private MockMvc mockMvc;
 
    private Amenity amenity;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(amenityController).build();
 
        amenity = new Amenity();
        amenity.setAmenityId(1L);
        amenity.setName("Pool");
        amenity.setDescription("Swimming pool");
    }
 
    @Test
    void testGetAllAmenities() throws Exception {
        List<Amenity> amenities = Arrays.asList(amenity);
        when(amenityService.getAllAmenities()).thenReturn(amenities);
 
        mockMvc.perform(get("/api/amenity/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pool"));
    }
 
    @Test
    void testGetAmenityById() throws Exception {
        when(amenityService.existsById(1L)).thenReturn(true);
        when(amenityService.getAmenityById(1L)).thenReturn(amenity);
 
        mockMvc.perform(get("/api/amenity/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pool"));
    }
 
    @Test
    void testCreateAmenity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String amenityJson = objectMapper.writeValueAsString(amenity);
 
        when(amenityService.findByAmenityname(amenity.getName())).thenReturn(Arrays.asList());
        doNothing().when(amenityService).saveAmenity(amenity);
 
        mockMvc.perform(post("/api/amenity/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(amenityJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Amenity added successfully"));
    }
 
    @Test
    void testUpdateAmenity() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String amenityJson = objectMapper.writeValueAsString(amenity);
 
        when(amenityService.existsById(1L)).thenReturn(true);
        doNothing().when(amenityService).updateAmenity(1L, amenity);
 
        mockMvc.perform(put("/api/amenity/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(amenityJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Amenity updated successfully"));
    }
 
    @Test
    void testDeleteAmenity() throws Exception {
        when(amenityService.existsById(1L)).thenReturn(true);
        doNothing().when(amenityService).deleteAmenity(1L);
 
        mockMvc.perform(delete("/api/amenity/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Amenity deleted successfully"));
    }
}