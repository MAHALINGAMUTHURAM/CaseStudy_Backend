package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.RoomDAO;
import com.model.Room;



@Service
public class RoomService {
	@Autowired
	RoomDAO roomDAO;
	List<Room> roomList;

	public RoomService() {
		roomList=new ArrayList<>();
	}
	
	public void addRoom(Room room)
	{
		roomList.add(room);
		roomDAO.save(room);
	}
	
	public List<Room> getAllRooms()
	{
		return roomDAO.findAll();
	}
	
	public void updateRoom(int id,Room room)
	{
		Room existing_room=roomDAO.findById(id).get();
		roomList.remove(existing_room);
		existing_room.setAvailable(room.isAvailable());
		existing_room.setRoom_number(room.getRoom_number());
		existing_room.setRoomtype(room.getRoomtype());
		
		roomList.add(existing_room);
		roomDAO.save(existing_room);
	}
	
	public void deleteRoom(int id)
	{
		Room room=roomDAO.findById(id).get();
		roomList.remove(room);
		roomDAO.delete(room);
	}
	
    public boolean findRoom(Room room) {
        if(roomList.contains(room))
        {
     	   return true;
        }
        return false;
     }
  
    public boolean findById(int id) {
        return roomDAO.findById(id).isPresent();
    }
    
    public Room getRoomById(int id)
    {
    	return roomDAO.findById(id).get();
    }
    
    public List<Room> findAvailableByRoomType(int roomTypeId) {
        return roomDAO.findByRoomtype_RoomTypeIdAndIsAvailable(roomTypeId,true);
    }
    
    public List<Room> findByLocation(String location) {
        return roomDAO.findRoomsByLocation(location);
    }

    

}
