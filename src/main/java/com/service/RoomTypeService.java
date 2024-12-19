package com.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dao.RoomTypeDAO;

import com.model.RoomType;


@Service
public class RoomTypeService {
	
	@Autowired
	RoomTypeDAO roomTypeDAO;
	List<RoomType> roomTypeList;
	
	public RoomTypeService() 
	{
		roomTypeList=new ArrayList<>();
	}
	
	public List<RoomType> getAllRoomType()
	{
		return roomTypeDAO.findAll();
	}
	
	
	
	
	  public void updateRoomType(int id, RoomType updatedRoomType) {
	        Optional<RoomType> existingRoomType = roomTypeDAO.findById(id);
	        if (existingRoomType.isPresent()) {
	            RoomType roomTypeToUpdate = existingRoomType.get();
	            
	            roomTypeToUpdate.setTypeName(updatedRoomType.getTypeName());
	            roomTypeToUpdate.setDescription(updatedRoomType.getDescription());
	            roomTypeToUpdate.setPricePerNight(updatedRoomType.getPricePerNight());
	            roomTypeDAO.save(roomTypeToUpdate);
	        } else {
	            throw new IllegalArgumentException("RoomType not found for ID: " + id);
	        }
	    }
	public void deleteRoomType(int id)
	{
		RoomType roomType=roomTypeDAO.findById(id).get();
		roomTypeDAO.delete(roomType);
	}
	public void saveRoomType(RoomType roomType) {
		roomTypeList.add(roomType);
	    roomTypeDAO.save(roomType);
	}
	
    public boolean findRoomType(RoomType roomType) {
       if(roomTypeList.contains(roomType))
       {
    	   return true;
       }
       return false;
    }
    public RoomType getRoomTypeById(int id)
    {
    	return roomTypeDAO.findById(id).get();
    }
    
    public boolean findById(int id) {
        return roomTypeDAO.findById(id).isPresent();
    }
    public boolean existsById(int id) {
        return roomTypeDAO.existsById(id);
    }
}
