package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Reservation;
import com.model.Room;
import com.service.ReservationService;
import com.service.RoomAmenityService;
import com.service.RoomService;
import com.service.TaskService;
import com.exception.CustomException;
import com.exception.Response;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RoomAmenityService roomAmenityService;
    
    @Autowired
    private ReservationService reservationService;

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
    
    @GetMapping("/rooms/hotels/{hotelId}/{startDate}/{endDate}")
    public ResponseEntity<?> getRoomByHotelId(@PathVariable Long hotelId,@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date startDate,
    		@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date endDate) throws CustomException{

        List<Room> rooms1 = roomService.findByHotel(hotelId);
        List<Reservation> rooms2 =  reservationService.getReservationsByDateRange(startDate, endDate);
        List<Room> rooms3=rooms1;
        for(Reservation r:rooms2)
        {
        	if(rooms1.contains(r.getRoom()))
        	{
        		rooms3.remove(r.getRoom());
        	}
        }
        if (rooms3.isEmpty()) {
            throw new CustomException("GETFAILS", "Rooms doesn't exist");
        }
        return ResponseEntity.ok(rooms3);
    
    }
    
    @GetMapping("/rooms/hotels/{hotelId}")
    public ResponseEntity<?> getRoomByHotelId(@PathVariable Long hotelId) throws CustomException{

        List<Room> rooms = roomService.findByHotel(hotelId);
        if (rooms.isEmpty()) {
            throw new CustomException("GETFAILS", "Rooms doesn't exist");
        }
        return ResponseEntity.ok(rooms);
    
    }
}
