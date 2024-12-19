package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        "{\"code\": \"ADDFAILS\", \"message\": \"Hotel already exists\"}"
                );
            }
            hotelService.addHotel(hotel);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    "{\"code\": \"POSTSUCCESS\", \"message\": \"Hotel added successfully\"}"
            );
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"ADDFAILS\", \"message\": \"Hotel already exists\"}"
            );
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllHotels() {
        try {
            List<Hotel> hotels = hotelService.getAllHotels();
            if (hotels.isEmpty()) {
                return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Hotel list is empty\"}");
            }
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching hotels\"}"
            );
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) {
        try {
            List<Room> rooms = hotelService.findByLocation(location);
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching rooms by location\"}"
            );
        }
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Object> getHotelById(@PathVariable("hotelId") Long hotelId) {
        try {
            Hotel hotel = hotelService.getHotelById(hotelId);
            if (hotel == null) {
                return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Hotel doesn't exist\"}");
            }
            return ResponseEntity.ok(hotel);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching hotel by ID\"}"
            );
        }
    }

    @GetMapping("/amenity/{amenityId}")
    public ResponseEntity<Object> getHotelsByAmenity(@PathVariable("amenityId") Long amenityId) {
        try {
            List<Hotel> hotels = hotelAmenityService.getHotelsByAmenity(amenityId);
            if (hotels.isEmpty()) {
                return ResponseEntity.status(404).body(
                        "{\"code\": \"GETFAILS\", \"message\": \"No hotel is found with the specific amenity\"}"
                );
            }
            return ResponseEntity.ok(hotels);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"GETFAILS\", \"message\": \"Error fetching hotels by amenity\"}"
            );
        }
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<Object> updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody Hotel hotel) {
        try {
            if (!hotelService.findById(hotelId)) {
                return ResponseEntity.status(404).body(
                        "{\"code\": \"UPDTFAILS\", \"message\": \"Hotel doesn't exist\"}"
                );
            }
            hotelService.updateHotel(hotelId, hotel);
            return ResponseEntity.ok(
                    "{\"code\": \"UPDATESUCCESS\", \"message\": \"Hotel updated successfully\"}"
            );
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    "{\"code\": \"UPDTFAILS\", \"message\": \"Error updating hotel\"}"
            );
        }
    }
}
