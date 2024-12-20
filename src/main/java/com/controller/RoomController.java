package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Room;
import com.service.RoomAmenityService;
import com.service.RoomService;
import com.service.TaskService;
import com.exception.CustomException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RoomAmenityService roomAmenityService;

    @PostMapping("/rooms/post")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            if (roomService.findRoom(room)) {
                throw new CustomException("ADDFAILS", "Room already exists");
            }

            roomService.addRoom(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "{\"code\": \"POSTSUCCESS\", \"message\": \"Room added successfully\"}"
            );
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"ADDFAILS\", \"message\": \"Error adding room\"}"
            );
        }
    }

    @GetMapping("/room/all")
    public ResponseEntity<?> getAllRooms() {
        try {
            List<Room> rooms = roomService.getAllRooms();
            if (rooms.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Room list is empty");
            }
            return ResponseEntity.ok(rooms);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching room list\"}"
            );
        }
    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long room_id) {
        try {
            if (!roomService.findById(room_id)) {
                throw new CustomException("GETFAILS", "Room doesn't exist");
            }
            return ResponseEntity.ok(roomService.getRoomById(room_id));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching room by ID\"}"
            );
        }
    }

    @GetMapping("/rooms/available/{roomTypeId}")
    public ResponseEntity<?> getAvailableRoomsByType(@PathVariable Long roomTypeId) {
        try {
            List<Room> availableRooms = roomService.findAvailableByRoomType(roomTypeId);
            if (availableRooms.isEmpty()) {
                throw new CustomException("GETFAILS", "No rooms found with the given type");
            }
            return ResponseEntity.ok(availableRooms);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching available rooms by type\"}"
            );
        }
    }

    @GetMapping("/rooms/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) {
        try {
            List<Room> rooms = roomService.findByLocation(location);
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching rooms by location\"}"
            );
        }
    }

    @GetMapping("/rooms/amenities/{amenityId}")
    public ResponseEntity<?> getRoomsByAmenity(@PathVariable Long amenityId) {
        try {
            List<Room> rooms = roomAmenityService.getRoomsByAmenity(amenityId);
            if (rooms.isEmpty()) {
                throw new CustomException("GETFAILS", "Amenity doesn't exist with the given ID");
            }
            return ResponseEntity.ok(rooms);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching rooms by amenity\"}"
            );
        }
    }

    @PutMapping("/room/update/{room_id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long room_id, @RequestBody Room room) {
        try {
            if (!roomService.findById(room_id)) {
                throw new CustomException("UPDTFAILS", "Room doesn't exist");
            }

            roomService.updateRoom(room_id, room);
            return ResponseEntity.ok(
                    "{\"code\": \"UPDATESUCCESS\", \"message\": \"Room updated successfully\"}"
            );
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"UPDTFAILS\", \"message\": \"Error updating room\"}"
            );
        }
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        try {
            if (!taskService.findById(taskId)) {
                throw new CustomException("DLTFAILS", "Task doesn't exist");
            }

            taskService.deleteTask(taskId);
            return ResponseEntity.ok(
                    "{\"code\": \"DELETESUCCESS\", \"message\": \"Task deleted successfully\"}"
            );
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}"
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"DLTFAILS\", \"message\": \"Error deleting task\"}"
            );
        }
    }
}
