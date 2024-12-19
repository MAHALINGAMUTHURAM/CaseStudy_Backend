package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.model.Amenity;

import com.service.AmenityService;
import com.service.HotelAmenityService;
import com.service.RoomAmenityService;

@RestController
@RequestMapping("/api/amenity")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;
    
    @Autowired
    private HotelAmenityService hotelamenityService;
    
    @Autowired
    private RoomAmenityService roomamenityService;
  

    @PostMapping("/post")
    public ResponseEntity<Object> createAmenity(@RequestBody Amenity amenity) {
        try {
            if (amenityService.findAmenity(amenity)) {
                return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"Amenity already exist\"}");
            }
            amenityService.saveAmenity(amenity);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Amenity added successfully\"}");
        } catch (Exception e) {
        	System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Error adding amenity\"}");
        }
    }
 
    @GetMapping("/all")
    public ResponseEntity<Object> getAllAmenities() {
        List<Amenity> amenities = amenityService.getAllAmenities();
        if (amenities.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETALLFAILS\", \"message\": \"Amenity list is empty\"}");
        }
        return ResponseEntity.ok(amenities);
    }


    @GetMapping("/{amenityId}")
    public ResponseEntity<Object> getAmenityById(@PathVariable("amenityId") long amenityId) {
        Amenity amenity = amenityService.getAmenityById(amenityId);
        if (amenity == null) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Amenity doesn't exist\"}");
        }
        return ResponseEntity.ok(amenity);
    }

    @PutMapping("/update/{AmenityId}")
    public ResponseEntity<Object> updateAmenity(@PathVariable("AmenityId") long amenityId, @RequestBody Amenity amenity) {
        if (!amenityService.findById(amenityId)) {
            return ResponseEntity.status(404).body("{\"code\": \"UPDTFAILS\", \"message\": \"Amenity doesn't exist\"}");
        }
        amenityService.updateAmenity(amenityId, amenity);
        return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"Amenity updated successfully\"}");
    }

   
    
    @DeleteMapping("/{amenityId}")
    public ResponseEntity<Object> deleteAmenity(@PathVariable("amenityId") long id) {
        if (!amenityService.existsById(id)) {
            return ResponseEntity.status(404).body("{\"code\": \"DLTFAILS\", \"message\": \"Amenity doesn't exist exist\"}");
        }
        try {
            amenityService.deleteAmenity(id);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Amenity deleted successfully\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"DLTFAILS\", \"message\": \"Amenity doesn't exist exist\"}");
        }
    }


    @GetMapping("/hotel/{hotel_id}")
    public ResponseEntity<?> getAmenitiesByHotel(@PathVariable long hotel_id) {
        List<Amenity> amenities = hotelamenityService.getAmenitiesByHotel(hotel_id);
        if (amenities.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETALLFAILS\", \"message\": \"hotel not found with given hotel id}");
        }
        return ResponseEntity.ok(amenities);
    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getAmenitiesByRoom(@PathVariable long room_id) {
        List<Amenity> amenities = roomamenityService.getAmenitiesByRoom(room_id);
        if (amenities.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETALLFAILS\", \"message\": \"room not found with given hotel id}");
        }
        return ResponseEntity.ok(amenities);
    }

}
