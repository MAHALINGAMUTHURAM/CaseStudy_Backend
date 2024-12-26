package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.HotelDAO;
import com.model.Hotel;


@Service
public class HotelService {
	
	@Autowired
	HotelDAO hotelDAO;
	
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
	
	public void saveHotel(Hotel hotel) {
	    hotelDAO.save(hotel);
	}
	
    public boolean existsById(long id) {
        return hotelDAO.existsById(id);
    }
    
    public Hotel getHotelById(long id)
    {
    	return hotelDAO.findById(id).get();
    }
    
    public boolean findByHotelLocation(String location) {
    	
        if(hotelDAO.findByLocation(location).isEmpty())
        {
        	return false;
        }
        
        return true;
    }
    
    public List<Hotel> findyArea(long id)
    {
    	return hotelDAO.findByArea_AreaId(id);
    }
}
