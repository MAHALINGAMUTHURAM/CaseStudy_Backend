package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exception.CustomException;
import com.model.Hotel;
import com.model.Room;
import com.service.HotelAmenityService;
import com.service.HotelService;


import java.util.List;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelAmenityService hotelAmenityService;

    @PostMapping("/post")
    public ResponseEntity<Object> addHotel(@RequestBody Hotel hotel) {
        try {
            if (hotelService.findHotel(hotel)) {
                throw new CustomException("ADDFAILS", "Hotel already exists");
            }
            hotelService.addHotel(hotel);
            return ResponseEntity.status(201).body(
                    "{\"code\": \"POSTSUCCESS\", \"message\": \"Hotel added successfully\"}"
            );
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Internal error occurred while adding hotel\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllHotels() {
        try {
            List<Hotel> hotels = hotelService.getAllHotels();
            if (hotels.isEmpty()) {
                throw new CustomException("GETFAILS", "Hotel list is empty");
            }
            return ResponseEntity.ok(hotels);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching hotels\"}");
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) {
        try {
            List<Room> rooms = hotelService.findByLocation(location);
            if (rooms.isEmpty()) {
                throw new CustomException("GETFAILS", "No rooms found for the given location");
            }
            return ResponseEntity.ok(rooms);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching rooms by location\"}");
        }
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Object> getHotelById(@PathVariable("hotelId") Long hotelId) {
        try {
            Hotel hotel = hotelService.getHotelById(hotelId);
            if (hotel == null) {
                throw new CustomException("GETFAILS", "Hotel doesn't exist");
            }
            return ResponseEntity.ok(hotel);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching hotel by ID\"}");
        }
    }

    @GetMapping("/amenity/{amenityId}")
    public ResponseEntity<Object> getHotelsByAmenity(@PathVariable("amenityId") Long amenityId) {
        try {
            List<Hotel> hotels = hotelAmenityService.getHotelsByAmenity(amenityId);
            if (hotels.isEmpty()) {
                throw new CustomException("GETFAILS", "No hotel found with the specific amenity");
            }
            return ResponseEntity.ok(hotels);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching hotels by amenity\"}");
        }
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<Object> updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody Hotel hotel) {
        try {
            if (!hotelService.findById(hotelId)) {
                throw new CustomException("UPDTFAILS", "Hotel doesn't exist");
            }
            hotelService.updateHotel(hotelId, hotel);
            return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"Hotel updated successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"UPDTFAILS\", \"message\": \"Error updating hotel\"}");
        }
    }
}
