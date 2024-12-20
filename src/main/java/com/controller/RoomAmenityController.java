package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.RoomAmenity;
import com.service.RoomAmenityService;
import com.exception.CustomException;

@RestController
@RequestMapping("/api/roomAmenity")
public class RoomAmenityController {

    @Autowired
    private RoomAmenityService roomAmenityService;

    @PostMapping("/post")
    public ResponseEntity<Object> createRoomAmenity(@RequestBody RoomAmenity roomAmenity) {
        try {
            if (roomAmenityService.findRoomAmenity(roomAmenity)) {
                throw new CustomException("ADDFAILS", "RoomAmenity already exists");
            }

            roomAmenityService.saveRoomAmenity(roomAmenity);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "{\"code\": \"POSTSUCCESS\", \"message\": \"RoomAmenity added successfully\"}"
            );
        } catch (CustomException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"code\": \"INVALIDARGUMENT\", \"message\": \"Invalid RoomAmenity data\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"ADDFAILS\", \"message\": \"Error adding RoomAmenity\"}"
            );
        }
    }
}
