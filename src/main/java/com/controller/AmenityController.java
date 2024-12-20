package com.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.model.Amenity;
import com.service.AmenityService;
import com.service.HotelAmenityService;
import com.service.RoomAmenityService;
import com.exception.CustomException;

@RestController
@RequestMapping("/api/amenity")
public class AmenityController {

    @Autowired
    private AmenityService amenityService;

    @Autowired
    private HotelAmenityService hotelAmenityService;
    
    @Autowired
    private RoomAmenityService roomAmenityService;

    @PostMapping("/post")
    public ResponseEntity<Object> createAmenity(@RequestBody Amenity amenity) {
        try {
            if (amenityService.findAmenity(amenity)) {
                throw new CustomException("ADDFAILS", "Amenity already exists");
            }
            amenityService.saveAmenity(amenity);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Amenity added successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Internal error occurred while adding amenity\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAmenities() {
        try {
            List<Amenity> amenities = amenityService.getAllAmenities();
            if (amenities.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Amenity list is empty");
            }
            return ResponseEntity.ok(amenities);
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching amenities\"}");
        }
    }

    @GetMapping("/{amenityId}")
    public ResponseEntity<Object> getAmenityById(@PathVariable("amenityId") Long amenityId) {
        try {
            Amenity amenity = amenityService.getAmenityById(amenityId);
            if (amenity == null) {
                throw new CustomException("GETFAILS", "Amenity doesn't exist");
            }
            return ResponseEntity.ok(amenity);
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching amenity\"}");
        }
    }

    @PutMapping("/update/{amenityId}")
    public ResponseEntity<Object> updateAmenity(@PathVariable("amenityId") Long amenityId, @RequestBody Amenity amenity) {
        try {
            if (!amenityService.findById(amenityId)) {
                throw new CustomException("UPDTFAILS", "Amenity doesn't exist");
            }
            amenityService.updateAmenity(amenityId, amenity);
            return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"Amenity updated successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"UPDTFAILS\", \"message\": \"Error updating amenity\"}");
        }
    }

    @DeleteMapping("/{amenityId}")
    public ResponseEntity<Object> deleteAmenity(@PathVariable("amenityId") Long amenityId) {
        try {
            if (!amenityService.existsById(amenityId)) {
                throw new CustomException("DLTFAILS", "Amenity doesn't exist");
            }
            amenityService.deleteAmenity(amenityId);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Amenity deleted successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"DLTFAILS\", \"message\": \"Error deleting amenity\"}");
        }
    }

    @GetMapping("/hotel/{hotel_id}")
    public ResponseEntity<?> getAmenitiesByHotel(@PathVariable Long hotel_id) {
        try {
            List<Amenity> amenities = hotelAmenityService.getAmenitiesByHotel(hotel_id);
            if (amenities.isEmpty()) {
                throw new CustomException("GETALLFAILS", "No amenities found for the given hotel");
            }
            return ResponseEntity.ok(amenities);
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching hotel amenities\"}");
        }
    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getAmenitiesByRoom(@PathVariable Long room_id) {
        try {
            List<Amenity> amenities = roomAmenityService.getAmenitiesByRoom(room_id);
            if (amenities.isEmpty()) {
                throw new CustomException("GETALLFAILS", "No amenities found for the given room");
            }
            return ResponseEntity.ok(amenities);
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching room amenities\"}");
        }
    }
}
