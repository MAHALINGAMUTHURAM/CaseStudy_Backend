package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exception.CustomException;
import com.exception.Response;
import com.model.HotelAmenity;
import com.service.HotelAmenityService;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/hotelamenity")
public class HotelAmenityController {
    
    @Autowired
    private HotelAmenityService hotelAmenityService;
    
    @PostMapping("/post")
    public ResponseEntity<Object> createHotelAmenity(@RequestBody HotelAmenity hotelAmenity) throws CustomException{
    	
            if (hotelAmenityService.findHotelAmenity(hotelAmenity)) {
                throw new CustomException("ADDFAILS", "HotelAmenity already exists");
            }
            hotelAmenityService.saveHotelAmenity(hotelAmenity);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","HotelAmenity added successfully"));
        
    }
}
