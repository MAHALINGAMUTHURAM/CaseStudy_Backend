package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.model.HotelAmenity;
import com.model.HotelAmenityId;    

@Repository
public interface HotelAmenityDAO extends JpaRepository<HotelAmenity, HotelAmenityId> {
	
	@Query("SELECT ha FROM HotelAmenity ha WHERE ha.amenity.id = :amenityId")
	List<HotelAmenity> findByAmenityId(Long amenityId);
}