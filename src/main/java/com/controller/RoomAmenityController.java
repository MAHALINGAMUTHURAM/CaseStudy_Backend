package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.RoomAmenity;
import com.service.RoomAmenityService;
import com.exception.CustomException;
import com.exception.Response;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/roomAmenity")
public class RoomAmenityController {

    @Autowired
    private RoomAmenityService roomAmenityService;

    @PostMapping("/post")
    public ResponseEntity<Object> createRoomAmenity(@RequestBody RoomAmenity roomAmenity) throws CustomException{

            if (roomAmenityService.findRoomAmenity(roomAmenity)) {
                throw new CustomException("ADDFAILS", "RoomAmenity already exists");
            }

            roomAmenityService.saveRoomAmenity(roomAmenity);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","RoomAmenity added successfully"));

    }
}
