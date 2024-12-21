package com.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RoomDAO;
import com.model.Room;



@Service
public class RoomService {
	@Autowired
	RoomDAO roomDAO;

	
	public void addRoom(Room room)
	{
		roomDAO.save(room);
	}
	
	public List<Room> getAllRooms()
	{
		return roomDAO.findAll();
	}
	
	public void updateRoom(long id,Room room)
	{
		Room existing_room=roomDAO.findById(id).get();
		existing_room.setAvailable(room.isAvailable());
		existing_room.setRoomNumber(room.getRoomNumber());
		existing_room.setRoomtype(room.getRoomtype());
		
		roomDAO.save(existing_room);
	}
	
	public void deleteRoom(long id)
	{
		Room room=roomDAO.findById(id).get();
		roomDAO.delete(room);
	}
	
    public List<Room> findRoomsByRoomNumber(int roomNo) {
    	
    	return roomDAO.findByRoomNumber(roomNo);
     }
  
    public boolean existsById(long id) {
        return roomDAO.findById(id).isPresent();
    }
    
    public Room getRoomById(long id)
    {
    	return roomDAO.findById(id).get();
    }
    
    public List<Room> findAvailableByRoomType(long roomTypeId) {
        return roomDAO.findByRoomtype_RoomTypeIdAndIsAvailable(roomTypeId,true);
    }
    
    public List<Room> findByLocation(String location) {
        return roomDAO.findRoomsByLocation(location);
    }

    

}
