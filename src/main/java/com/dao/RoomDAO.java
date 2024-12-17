package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.model.Room;
@Repository
public interface RoomDAO extends JpaRepository<Room, Long> {

	//@Query("SELECT r FROM Room r WHERE r.roomType.id = :roomTypeId AND r.isAvailable = :isAvailable")
	List<Room> findByRoomtype_RoomTypeIdAndIsAvailable(Long typeId, boolean available);
	
    @Query("SELECT r FROM Room r WHERE r.location = :location")
    List<Room> findRoomsByLocation(String location);
	
}
