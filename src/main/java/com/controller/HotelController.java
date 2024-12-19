package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private HotelAmenityService hotelamenityService;

    @PostMapping("/post")
    public ResponseEntity<Object> addHotel(@RequestBody Hotel hotel) {
    	
//        try {
//            if (hotelService.findHotel(hotel)) {
//                return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"Hotel already exists\"}");
//            }
//            hotelService.saveHotel(hotel);
//            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Hotel added successfully\"}");
//        } catch (Exception e) {
//        	System.out.println(e);
//            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Error adding hotel\"}");
//        }
    	
        if (hotelService.findHotel(hotel)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            		"{\"code\": \"ADDFAILS\", \"message\": \"Hotel already exists\"}"
            );
        }

        hotelService.addHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(
        		"{\"code\": \"POSTSUCCESS\", \"message\": \"Hotel added successfully\"}"
        		);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        if (hotels.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Hotel list is empty\"}");
        }
        return ResponseEntity.ok(hotels);
    }
    
    @GetMapping("/location/{location}")
    public ResponseEntity<?> getRoomsByLocation(@PathVariable String location) {
        List<Room> rooms = hotelService.findByLocation(location);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Object> getHotelById(@PathVariable("hotelId") int hotelId) {
    
        if (!hotelService.findById(hotelId)) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Hotel doesn't exist\"}");
        }
        return ResponseEntity.ok(hotelService.getHotelById(hotelId));
    }

    @GetMapping("/amenity/{amenityId}")
    public ResponseEntity<Object> getHotelsByAmenity(@PathVariable("amenityId") int amenityId) {
        List<Hotel> hotels = hotelamenityService.getHotelsByAmenity(amenityId);
        if (hotels.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"No hotel is found with the specific amenity\"}");
        }
        return ResponseEntity.ok(hotels);
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<Object> updateHotel(@PathVariable("hotelId") int hotelId, @RequestBody Hotel hotel) {
        if (!hotelService.findById(hotelId)) {
            return ResponseEntity.status(404).body("{\"code\": \"UPDTFAILS\", \"message\": \"Hotel doesn't exist\"}");
        }
        hotelService.updateHotel(hotelId, hotel);
        return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"Hotel updated successfully\"}");
    }
}