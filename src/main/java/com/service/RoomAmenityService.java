package com.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RoomAmenityDAO;
import com.model.Room;
import com.model.RoomAmenity;

@Service
public class RoomAmenityService{
	@Autowired
	RoomAmenityDAO roomamenityDAO;
	
	public RoomAmenityService() {
		
	}
	public void add(RoomAmenity roomamenity)
	{
		roomamenityDAO.save(roomamenity);
	}
	public List<RoomAmenity> getAll()
	{
		return roomamenityDAO.findAll();
	}
	public void update(RoomAmenity roomamenity)
	{
		roomamenityDAO.save(roomamenity);
	}
	
    public List<Room> getRoomsByAmenity(Long amenityId) {

        List<RoomAmenity> roomAmenities = roomamenityDAO.findByAmenityId(amenityId);
        
        return roomAmenities.stream()
                             .map(RoomAmenity::getRoom)
                             .collect(Collectors.toList());
    }
	
}