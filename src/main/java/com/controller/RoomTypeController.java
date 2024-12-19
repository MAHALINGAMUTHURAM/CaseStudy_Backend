package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"RoomType added successfully\"}");
        } catch (Exception e) {
            System.out.println("Error in createHotel: " + e.getMessage());
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"RoomType already exist\"}");
        }
    }

    @GetMapping("/{RoomType_id}")
    public ResponseEntity<Object> getAllRoomType() {
        try {
            List<RoomType> roomTypes = roomTypeService.getAllRoomType();
            if (roomTypes.isEmpty()) {
                return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"RoomType doesn't exist\"}");
            }
            return ResponseEntity.ok(roomTypes);
        } catch (Exception e) {
            System.out.println("Error in getAllRoomType: " + e.getMessage());
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching RoomTypes\"}");
        }
    }

    @DeleteMapping("/delete/{RoomType_id}")
    public ResponseEntity<Object> deleteRoomType(@PathVariable("RoomType_id") Long id) {
        try {
            if (!roomTypeService.existsById(id)) {
                return ResponseEntity.status(404).body("{\"code\": \"DLTFAILS\", \"message\": \"RoomType doesn't exist\"}");
            }
            roomTypeService.deleteRoomType(id);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"RoomType deleted successfully\"}");
        } catch (Exception e) {
            System.out.println("Error in deleteRoomType: " + e.getMessage());
            return ResponseEntity.status(500).body("{\"code\": \"DLTFAILS\", \"message\": \"Error deleting RoomType\"}");
        }
    }

    @PutMapping("/update/{roomTypeId}")
    public ResponseEntity<Object> updateRoomType(@PathVariable("roomTypeId") Long roomTypeId, @RequestBody RoomType roomType) {
        try {
            if (!roomTypeService.findById(roomTypeId)) {
                return ResponseEntity.status(404).body("{\"code\": \"UPDTFAILS\", \"message\": \"RoomType doesn't exist\"}");
            }
            roomTypeService.updateRoomType(roomTypeId, roomType);
            return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"RoomType updated successfully\"}");
        } catch (Exception e) {
            System.out.println("Error in updateRoomType: " + e.getMessage());
            return ResponseEntity.status(500).body("{\"code\": \"UPDTFAILS\", \"message\": \"Error updating RoomType\"}");
        }
    }
}
