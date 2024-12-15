package com.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.HotelAmenityDAO;
import com.model.Hotel;
import com.model.HotelAmenity;

@Service
public class HotelAmenityService {
    @Autowired
    HotelAmenityDAO hotelAmenityDAO;
    
    public void add(HotelAmenity hotelAmenity) {
        hotelAmenityDAO.save(hotelAmenity);
    }
    
    public List<Hotel> getHotelsByAmenity(Long amenityId) {

        List<HotelAmenity> hotelAmenities = hotelAmenityDAO.findByAmenityId(amenityId);
        
        return hotelAmenities.stream()
                             .map(HotelAmenity::getHotel)
                             .collect(Collectors.toList());
    }
}
