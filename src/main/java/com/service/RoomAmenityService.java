package com.service;

import com.dao.RoomAmenityDAO;
import com.model.Amenity;
import com.model.HotelAmenity;
import com.model.Room;
import com.model.RoomAmenity;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomAmenityService {

    @Autowired
    private RoomAmenityDAO roomAmenityDAO;

List<RoomAmenity> roomAmenityList;
	
	public RoomAmenityService() 
	{
		roomAmenityList=new ArrayList<>();
	}

    public void saveRoomAmenity(RoomAmenity roomAmenity) {
		roomAmenityList.add(roomAmenity);
	    roomAmenityDAO.save(roomAmenity);
	}
    
    public boolean findRoomAmenity(RoomAmenity roomAmenity) {
        if(roomAmenityList.contains(roomAmenity))
        {
     	   return true;
        }
        return false;
     }
    
    public List<Room> getRoomsByAmenity(int amenityId) {
    	
    	List<RoomAmenity> roomAmenities = roomAmenityDAO.findByAmenity_AmenityId(amenityId);
        return roomAmenities.stream()
                             .map(RoomAmenity::getRoom)
                             .collect(Collectors.toList());
    }
    
	public List<Amenity> getAmenitiesByRoom(int roomId) {
	    List<RoomAmenity> hotelAmenities = roomAmenityDAO.findByRoom_RoomId(roomId);

	    return hotelAmenities.stream()
	            .map(RoomAmenity::getAmenity)
	            .collect(Collectors.toList());
	}
}
