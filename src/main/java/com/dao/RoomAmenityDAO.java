package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.HotelAmenity;
import com.model.RoomAmenity;
import com.model.RoomAmenityId;

@Repository
public interface RoomAmenityDAO extends  JpaRepository<RoomAmenity, RoomAmenityId> {
	
	List<RoomAmenity> findByAmenity_AmenityId(Long amenityId);

	List<RoomAmenity> findByRoom_RoomId(Long roomId);
}
