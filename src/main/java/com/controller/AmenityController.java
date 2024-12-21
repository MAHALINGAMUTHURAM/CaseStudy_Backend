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
import com.exception.Response;

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
    public ResponseEntity<Object> createAmenity(@RequestBody Amenity amenity) throws CustomException {
    	
            if (!amenityService.findByAmenityname(amenity.getName()).isEmpty()) {
                throw new CustomException("ADDFAILS", "Amenity already exists");
            }
            amenityService.saveAmenity(amenity);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","Amenity added successfully"));
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllAmenities() throws CustomException{

            List<Amenity> amenities = amenityService.getAllAmenities();
            if (amenities.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Amenity list is empty");
            }
            return ResponseEntity.ok(amenities);
        
    }

    @GetMapping("/{amenityId}")
    public ResponseEntity<Object> getAmenityById(@PathVariable("amenityId") Long amenityId) throws CustomException{
    	

            if (!amenityService.existsById(amenityId)) {
                throw new CustomException("GETFAILS", "Amenity doesn't exist");
            }
            return ResponseEntity.ok(amenityService.getAmenityById(amenityId));
    }

    @PutMapping("/update/{amenityId}")
    public ResponseEntity<Object> updateAmenity(@PathVariable("amenityId") Long amenityId, @RequestBody Amenity amenity) throws CustomException{

            if (!amenityService.existsById(amenityId)) {
                throw new CustomException("UPDTFAILS", "Amenity doesn't exist");
            }
            amenityService.updateAmenity(amenityId, amenity);
            return ResponseEntity.ok(new Response("UPDATESUCCESS","Amenity updated successfully"));

    }

    @DeleteMapping("/{amenityId}")
    public ResponseEntity<Object> deleteAmenity(@PathVariable("amenityId") Long amenityId) throws CustomException{

            if (!amenityService.existsById(amenityId)) {
                throw new CustomException("DLTFAILS", "Amenity doesn't exist");
            }
            amenityService.deleteAmenity(amenityId);
            return ResponseEntity.ok(new Response("DELETESUCCESS","Amenity deleted successfully"));
       
    }

    @GetMapping("/hotel/{hotel_id}")
    public ResponseEntity<?> getAmenitiesByHotel(@PathVariable Long hotel_id) throws CustomException{

            List<Amenity> amenities = hotelAmenityService.getAmenitiesByHotel(hotel_id);
            if (amenities.isEmpty()) {
                throw new CustomException("GETALLFAILS", "No amenities found for the given hotel");
            }
            return ResponseEntity.ok(amenities);
     
    }

    @GetMapping("/room/{room_id}")
    public ResponseEntity<?> getAmenitiesByRoom(@PathVariable Long room_id) throws CustomException{
            List<Amenity> amenities = roomAmenityService.getAmenitiesByRoom(room_id);
            if (amenities.isEmpty()) {
                throw new CustomException("GETALLFAILS", "No amenities found for the given room");
            }
            return ResponseEntity.ok(amenities);
        }
}