package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.Amenity;
import com.model.HotelAmenity;
import com.model.HotelAmenityId;    

@Repository
public interface HotelAmenityDAO extends JpaRepository<HotelAmenity, HotelAmenityId> {
	
	 List<HotelAmenity> findByAmenity_AmenityId(int amenityId);
	
	 List<HotelAmenity> findByHotel_HotelId(int hotelId);}