package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.model.RoomAmenity;
import com.model.RoomAmenityId;

@Repository
public interface RoomAmenityDAO extends  JpaRepository<RoomAmenity, RoomAmenityId> {
	
	@Query("SELECT ha FROM RoomAmenity ha WHERE ha.amenity.id = :amenityId")
	List<RoomAmenity> findByAmenityId(Long amenityId);

}
