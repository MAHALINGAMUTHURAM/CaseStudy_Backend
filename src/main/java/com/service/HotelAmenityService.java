package com.service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.HotelAmenityDAO;
import com.model.Amenity;
import com.model.Hotel;
import com.model.HotelAmenity;

@Service
public class HotelAmenityService {
    @Autowired
    HotelAmenityDAO hotelAmenityDAO;
    List<HotelAmenity> hotelamenityList;
    public HotelAmenityService()
    {
    	hotelamenityList=new ArrayList<>();
    }
    
    public void add(HotelAmenity hotelAmenity) {
        hotelAmenityDAO.save(hotelAmenity);
    }
    
    public List<Hotel> getHotelsByAmenity(int amenityId) {

        List<HotelAmenity> hotelAmenities = hotelAmenityDAO.findByAmenity_AmenityId(amenityId);
        
        return hotelAmenities.stream()
                             .map(HotelAmenity::getHotel)
                             .collect(Collectors.toList());
    }
    
    
    public boolean findHotelAmenity(HotelAmenity hotelamenity)
    {
    	if(hotelamenityList.contains(hotelamenity))
    	{
    		return true;
    	}
    	return false;
    }
	
	public List<Amenity> getAmenitiesByHotel(int hotelId) {
	    List<HotelAmenity> hotelAmenities = hotelAmenityDAO.findByHotel_HotelId(hotelId);

	    return hotelAmenities.stream()
	            .map(HotelAmenity::getAmenity)
	            .collect(Collectors.toList());
	}
	
}
