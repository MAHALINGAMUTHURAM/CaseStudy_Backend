package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exception.CustomException;
import com.exception.Response;
import com.model.Hotel;
import com.service.HotelAmenityService;
import com.service.HotelService;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelAmenityService hotelAmenityService;

    @PostMapping("/post")
    public ResponseEntity<Object> addHotel(@RequestBody Hotel hotel) throws CustomException{

            if (!hotelService.findByHotelLocation(hotel.getLocation())) {
                throw new CustomException("ADDFAILS", "Hotel already exists");
            }
            hotelService.saveHotel(hotel);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","Hotel added successfully"));

            
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllHotels() throws CustomException {
    	
            List<Hotel> hotels = hotelService.getAllHotels();
            if (hotels.isEmpty()) {
                throw new CustomException("GETFAILS", "Hotel list is empty");
            }
            return ResponseEntity.ok(hotels);

    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<Object> getHotelById(@PathVariable("hotelId") Long hotelId) throws CustomException{

            if (!hotelService.existsById(hotelId)) {
                throw new CustomException("GETFAILS", "Hotel doesn't exist");
            }
            return ResponseEntity.ok(hotelService.getHotelById(hotelId));
        
    }

    @GetMapping("/amenity/{amenityId}")
    public ResponseEntity<Object> getHotelsByAmenity(@PathVariable("amenityId") Long amenityId) throws CustomException{

            List<Hotel> hotels = hotelAmenityService.getHotelsByAmenity(amenityId);
            if (hotels.isEmpty()) {
                throw new CustomException("GETFAILS", "No hotel found with the specific amenity");
            }
            return ResponseEntity.ok(hotels);
    }

    @PutMapping("/update/{hotelId}")
    public ResponseEntity<Object> updateHotel(@PathVariable("hotelId") Long hotelId, @RequestBody Hotel hotel) throws CustomException{

            if (!hotelService.existsById(hotelId)) {
                throw new CustomException("UPDTFAILS", "Hotel doesn't exist");
            }
            hotelService.updateHotel(hotelId, hotel);
            return ResponseEntity.ok(new Response("UPDATESUCCESS","Hotel updated successfully"));
        
    }
    
    @GetMapping("/area/{areaId}")
    public ResponseEntity<Object> getHotelsByArea(@PathVariable("areaId") Long areaId) throws CustomException{

            List<Hotel> hotels = hotelService.findyArea(areaId);
            if (hotels.isEmpty()) {
                throw new CustomException("GETFAILS", "No hotel found with the specific Area");
            }
            return ResponseEntity.ok(hotels);
    }
    
    @DeleteMapping("/delete/{hotelId}")
    public ResponseEntity<Object> deleteHotel(@PathVariable("hotelId") Long hotelId) throws CustomException{

        if (!hotelService.existsById(hotelId)) {
            throw new CustomException("DLTFAILS", "Hotel doesn't exist");
        }
        hotelService.deleteHotel(hotelId);
        return ResponseEntity.ok(new Response("DELETESUCCESS","Hotel deleted successfully"));
}
    
    @GetMapping("/areaAmenity/{areaId}/{amenityId}")
    public ResponseEntity<Object> getHotelsByAreaAmenity(@PathVariable("areaId") Long areaId,@PathVariable("amenityId") Long amenityId) throws CustomException{

            List<Hotel> hotels1 = hotelService.findyArea(areaId);
            List<Hotel> hotels2=  hotelAmenityService.getHotelsByAmenity(amenityId);
            List<Hotel> hotels3= new ArrayList<>();
            System.out.println(hotels1);
            System.out.println(hotels2);
            for(Hotel h:hotels1)
            {
            	if(hotels2.contains(h))
            	{
            		hotels3.add(h);
            	}
            }
            if (hotels3.isEmpty()) {
                throw new CustomException("GETFAILS", "No hotel found with the specific Area");
            }
            return ResponseEntity.ok(hotels3);
    }
}
