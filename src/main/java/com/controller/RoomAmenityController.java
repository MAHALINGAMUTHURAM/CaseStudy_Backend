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
                return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"RoomAmenity already exist\"}");
            }
            roomAmenityService.saveRoomAmenity(roomAmenity);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"RoomAmenity added successfully\"}");
        } catch (Exception e) {
        	System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"RoomAmenity already exist\"}");
        }
    }
}
