package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.HotelDAO;
import com.model.Hotel;
import com.model.Room;


@Service
public class HotelService {
	
	@Autowired
	HotelDAO hotelDAO;
	List<Hotel> hotelList;
	
	public HotelService() 
	{
		hotelList=new ArrayList<>();
	}
	
	public List<Hotel> getAllHotels()
	{
		return hotelDAO.findAll();
	}
	
	public void updateHotel(long id, Hotel hotel) 
	{
	        Hotel existingHotel = hotelDAO.findById(id).get();

	        existingHotel.setName(hotel.getName());
	        existingHotel.setLocation(hotel.getLocation());
	        existingHotel.setDescription(hotel.getDescription());

	        hotelDAO.save(existingHotel);
	}
	public void deleteHotel(long id)
	{
		Hotel hotel=hotelDAO.findById(id).get();
		hotelDAO.delete(hotel);
	}
	public void addHotel(Hotel hotel) {
		hotelList.add(hotel);
	    hotelDAO.save(hotel);
	}
	
    public boolean findHotel(Hotel hotel) {
       if(hotelList.contains(hotel))
       {
    	   return true;
       }
       return false;
    }
    
    public Hotel getHotelById(long id)
    {
    	return hotelDAO.findById(id).get();
    }
    
    public boolean findById(long id) {
        return hotelDAO.findById(id).isPresent();
    }
    
    public List<Room> findByLocation(String location) {
        return hotelDAO.findHotelsByLocation(location);
    }
}
