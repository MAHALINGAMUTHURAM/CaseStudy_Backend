package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exception.CustomException;
import com.model.HotelAmenity;
import com.service.HotelAmenityService;


@RestController
@RequestMapping("/api/hotelamenity")
public class HotelAmenityController {
    
    @Autowired
    private HotelAmenityService hotelAmenityService;
    
    @PostMapping("/post")
    public ResponseEntity<Object> createHotelAmenity(@RequestBody HotelAmenity hotelAmenity) {
        try {
            if (hotelAmenityService.findHotelAmenity(hotelAmenity)) {
                throw new CustomException("ADDFAILS", "HotelAmenity already exists");
            }
            hotelAmenityService.add(hotelAmenity);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"HotelAmenity added successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Internal error occurred while adding HotelAmenity\"}");
        }
    }
}
