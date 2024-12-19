package com.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.RoomAmenity;

import com.service.RoomAmenityService;

@RestController
@RequestMapping("/api/roomAmenity")
public class RoomAmenityController {

    @Autowired
    private RoomAmenityService roomAmenityService;


    @PostMapping("/post")
    public ResponseEntity<Object> createRoomAmenity(@RequestBody RoomAmenity roomAmenity) {
        try {
          
            if (roomAmenityService.findRoomAmenity(roomAmenity)) {
                return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"RoomAmenity already exists\"}");
            }

          
            roomAmenityService.saveRoomAmenity(roomAmenity);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"RoomAmenity added successfully\"}");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid argument: " + e.getMessage()); 
            return ResponseEntity.badRequest().body("{\"code\": \"INVALIDARGUMENT\", \"message\": \"Invalid RoomAmenity data\"}");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e); 
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"RoomAmenity already exists\"}");
        }
    }

}
