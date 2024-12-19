package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Room;
import com.service.RoomAmenityService;
import com.service.RoomService;
import com.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {
	
	@Autowired
    private RoomService roomService;
	@Autowired
    private TaskService taskService;
	@Autowired
	private RoomAmenityService roomamenityService;

    @PostMapping("/rooms/post")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        if (roomService.findRoom(room)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            		"{\"code\": \"ADDFAILS\", \"message\": \"Room already exists\"}"
            );
        }

        roomService.addRoom(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(
        		"{\"code\": \"POSTSUCCESS\", \"message\": \"Room added successfully\"}");
    }

    @GetMapping("/room/all")
    public ResponseEntity<?> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            		"{\"code\": \"GETALLFAILS\", \"message\": \"Room list is empty\"}"
            );
        }
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getRoomById(@PathVariable long room_id) {
        if (!roomService.findById(room_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            		"{\"code\": \"GETALLFAILS\", \"message\": \"Room doesn't exist\"}"
            );
        }
        return ResponseEntity.ok(roomService.getRoomById(room_id));
    }

    @GetMapping("/rooms/available/{roomTypeId}")
    public ResponseEntity<?> getAvailableRoomsByType(@PathVariable long roomTypeId) {
        List<Room> availableRooms = roomService.findAvailableByRoomType(roomTypeId);
        if (availableRooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            		"{\"code\": \"GETALLFAILS\", \"message\": \"No room found with given type\"}"
            );
        }
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping("/rooms/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) {
        List<Room> rooms = roomService.findByLocation(location);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/rooms/amenities/{amenityId}")
    public ResponseEntity<?> getRoomsByAmenity(@PathVariable long amenityId) {
        List<Room> rooms = roomamenityService.getRoomsByAmenity(amenityId);
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            		"{\"code\": \"GETALLFAILS\", \"message\": \"Amenity doesn't exist with given ID\"}"
            );
        }
        return ResponseEntity.ok(rooms);
    }

    @PutMapping("/room/update/{room_id}")
    public ResponseEntity<?> updateRoom(@PathVariable long room_id, @RequestBody Room room) {
        if (!roomService.findById(room_id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            		"{\"code\": \"UPDTFAILS\", \"message\": \"Room doesn't exist\"}"
            );
        }
        
        roomService.updateRoom(room_id,room);
        return ResponseEntity.ok(
        		"{\"code\": \"UPDATESUCCESS\", \"message\": \"Room updated successfully\"}"
        );
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable long taskId) {
        if (!taskService.findById(taskId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            		"{\"code\": \"DLTFAILS\", \"message\": \"Task doesn't exist\"}"
            );
        }

        taskService.deleteTask(taskId);
        return ResponseEntity.ok(
        		"{\"code\": \"DELETESUCCESS\", \"message\": \"Task deleted successfully\"}"
        );
    }
}
    
