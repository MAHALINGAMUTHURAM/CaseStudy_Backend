package com.controller;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.RoomType;

import com.service.RoomTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;
   

    @PostMapping("/post")
    public ResponseEntity<Object> createHotel(@RequestBody RoomType roomType) {
        try {
            if (roomTypeService.findRoomType(roomType)) {
                return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"RoomType already exist\"}");
            }
            roomTypeService.saveRoomType(roomType);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"roomType added successfully\"}");
        } catch (Exception e) {
        	System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"roomType already exist\"}");
        }
    }

    @GetMapping("/{RoomType_id}")
    public ResponseEntity<Object> getAllRoomType() {
        List<RoomType> roomTypes = roomTypeService.getAllRoomType();
        if (roomTypes.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"RoomType doesn't exist\"}");
        }
        return ResponseEntity.ok(roomTypes);
    }

    @DeleteMapping("/delete/{RoomType_id}")
    public ResponseEntity<Object> deleteRoomType(@PathVariable("RoomType_id") long id) {
        if (!roomTypeService.existsById(id)) {
            return ResponseEntity.status(404).body("{\"code\": \"DLTFAILS\", \"message\": \"Project doesn't exist exist\"}");
        }
        try {
            roomTypeService.deleteRoomType(id);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Project deleted successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"DLTFAILS\", \"message\": \"Project doesn't exist exist\"}");
        }
    }

    @PutMapping("/update/{roomTypeId}")
    public ResponseEntity<Object> updateRoomType(@PathVariable("roomTypeId") long roomTypeId, @RequestBody RoomType roomType) {
        if (!roomTypeService.findById(roomTypeId)) {
            return ResponseEntity.status(404).body("{\"code\": \"UPDTFAILS\", \"message\": \"RoomType doesn't exist\"}");
        }
        roomTypeService.updateRoomType(roomTypeId, roomType);
        return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"RoomType updated successfully\"}");
    }
}
