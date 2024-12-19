package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.model.Hotel;
import com.model.Room;

@Repository
public interface HotelDAO extends JpaRepository<Hotel, Integer> {
	
    @Query("SELECT r FROM Hotel r WHERE r.location = :location")
    List<Room> findHotelsByLocation(String location);
    
}