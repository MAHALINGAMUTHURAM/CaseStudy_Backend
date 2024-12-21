package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Room;
import com.service.RoomAmenityService;
import com.service.RoomService;
import com.service.TaskService;
import com.exception.CustomException;
import com.exception.Response;

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
    public ResponseEntity<?> createRoom(@RequestBody Room room) throws CustomException{

            if (!roomService.findRoomsByRoomNumber(room.getRoomNumber()).isEmpty()) {
                throw new CustomException("ADDFAILS", "Room already exists");
            }

            roomService.saveRoom(room);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","Room added successfully"));

            
    }

    @GetMapping("/room/all")
    public ResponseEntity<?> getAllRooms() throws CustomException{

            List<Room> rooms = roomService.getAllRooms();
            if (rooms.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Room list is empty");
            }
            return ResponseEntity.ok(rooms);

    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long room_id) throws CustomException{

            if (!roomService.existsById(room_id)) {
                throw new CustomException("GETFAILS", "Room doesn't exist");
            }
            return ResponseEntity.ok(roomService.getRoomById(room_id));

    }

    @GetMapping("/rooms/available/{roomTypeId}")
    public ResponseEntity<?> getAvailableRoomsByType(@PathVariable Long roomTypeId) throws CustomException{

            List<Room> availableRooms = roomService.findAvailableByRoomType(roomTypeId);
            if (availableRooms.isEmpty()) {
                throw new CustomException("GETFAILS", "No rooms found with the given type");
            }
            return ResponseEntity.ok(availableRooms);

    }

    @GetMapping("/rooms/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) throws CustomException{

            List<Room> rooms = roomService.findByLocation(location);
            if(rooms.isEmpty())
            {
            	throw new CustomException("GETFAILS", "No rooms found with the given location");
            }
            return ResponseEntity.ok(rooms);

    }

    @GetMapping("/rooms/amenities/{amenityId}")
    public ResponseEntity<?> getRoomsByAmenity(@PathVariable Long amenityId) throws CustomException{

            List<Room> rooms = roomAmenityService.getRoomsByAmenity(amenityId);
            if (rooms.isEmpty()) {
                throw new CustomException("GETFAILS", "Amenity doesn't exist with the given ID");
            }
            return ResponseEntity.ok(rooms);

    }

    @PutMapping("/room/update/{room_id}")
    public ResponseEntity<?> updateRoom(@PathVariable Long room_id, @RequestBody Room room) throws CustomException{

            if (!roomService.existsById(room_id)) {
                throw new CustomException("UPDTFAILS", "Room doesn't exist");
            }

            roomService.updateRoom(room_id, room);
            return ResponseEntity.ok(new Response("UPDATESUCCESS","Room updated successfully"));

    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) throws CustomException{

            if (!taskService.findById(taskId)) {
                throw new CustomException("DLTFAILS", "Task doesn't exist");
            }

            taskService.deleteTask(taskId);
            return ResponseEntity.ok(new Response("DELETESUCCESS","Task deleted successfully"));


    }
}
