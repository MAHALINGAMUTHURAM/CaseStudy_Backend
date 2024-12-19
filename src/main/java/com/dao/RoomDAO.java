package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.model.Room;
@Repository
public interface RoomDAO extends JpaRepository<Room, Integer> {

	//@Query("SELECT r FROM Room r WHERE r.roomType.id = :roomTypeId AND r.isAvailable = :isAvailable")
	List<Room> findByRoomtype_RoomTypeIdAndIsAvailable(int typeId, boolean available);
	
//    @Query("SELECT r FROM Room r WHERE r.location = :location")
    List<Room> findRoomsByLocation(String location);
	
}
