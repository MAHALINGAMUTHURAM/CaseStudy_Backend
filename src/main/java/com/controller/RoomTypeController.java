package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.RoomType;
import com.service.RoomTypeService;
import com.exception.CustomException;

import java.util.List;

@RestController
@RequestMapping("/api/RoomType")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping("/post")
    public ResponseEntity<Object> createRoomType(@RequestBody RoomType roomType) {
        try {
            if (roomTypeService.findRoomType(roomType)) {
                throw new CustomException("ADDFAILS", "RoomType already exists");
            }
            roomTypeService.saveRoomType(roomType);
            return ResponseEntity.status(HttpStatus.CREATED).body("{\"code\": \"POSTSUCCESS\", \"message\": \"RoomType added successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"code\": \"ADDFAILS\", \"message\": \"Error adding RoomType\"}");
        }
    }

    @GetMapping("/{RoomType_id}")
    public ResponseEntity<Object> getRoomTypeById(@PathVariable("RoomType_id") Long roomTypeId) {
        try {
            RoomType roomType = roomTypeService.getRoomTypeById(roomTypeId);
            if (roomType == null) {
                throw new CustomException("GETFAILS", "RoomType doesn't exist");
            }
            return ResponseEntity.ok(roomType);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching RoomType\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoomTypes() {
        try {
            List<RoomType> roomTypes = roomTypeService.getAllRoomType();
            if (roomTypes.isEmpty()) {
                throw new CustomException("GETFAILS", "RoomTypes list is empty");
            }
            return ResponseEntity.ok(roomTypes);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching RoomTypes\"}");
        }
    }

    @DeleteMapping("/delete/{RoomType_id}")
    public ResponseEntity<Object> deleteRoomType(@PathVariable("RoomType_id") Long id) {
        try {
            if (!roomTypeService.existsById(id)) {
                throw new CustomException("DLTFAILS", "RoomType doesn't exist");
            }
            roomTypeService.deleteRoomType(id);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"RoomType deleted successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"code\": \"DLTFAILS\", \"message\": \"Error deleting RoomType\"}");
        }
    }

    @PutMapping("/update/{roomTypeId}")
    public ResponseEntity<Object> updateRoomType(@PathVariable("roomTypeId") Long roomTypeId, @RequestBody RoomType roomType) {
        try {
            if (!roomTypeService.findById(roomTypeId)) {
                throw new CustomException("UPDTFAILS", "RoomType doesn't exist");
            }
            roomTypeService.updateRoomType(roomTypeId, roomType);
            return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"RoomType updated successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"code\": \"UPDTFAILS\", \"message\": \"Error updating RoomType\"}");
        }
    }
}
