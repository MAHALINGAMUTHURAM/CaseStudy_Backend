package com.service;

import com.dao.RoomAmenityDAO;

import com.model.RoomAmenity;


import java.util.ArrayList;
import java.util.List;

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
}
