package com.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public void update(long id,Room room)
	{
		Room existing_room=roomDAO.findById(id).get();
		roomList.remove(existing_room);
		existing_room.setIs_available(room.isIs_available());
		existing_room.setRoom_number(room.getRoom_number());
		existing_room.setRoomtype(room.getRoomtype());
		
		roomList.add(existing_room);
		roomDAO.save(existing_room);
	}
	
	public void delete(Long id)
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
  
    public boolean findById(long id) {
        return roomDAO.findById(id).isPresent();
    }
    
    public Room getRoomById(long id)
    {
    	return roomDAO.findById(id).get();
    }
    
    public List<Room> findAvailableByRoomType(Long roomTypeId) {
        return roomDAO.findRoomsByTypeAndAvailability(roomTypeId,true);
    }
    
    public List<Room> findByLocation(String location) {
        return roomDAO.findRoomsByLocation(location);
    }

    

}
