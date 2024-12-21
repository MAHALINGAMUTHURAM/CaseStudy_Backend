package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.model.Reservation;
import com.model.Room;
@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long> {

	List<Reservation> findByCheckInDateBetween(java.util.Date startDate, java.util.Date endDate);
	List<Room> findByRoom_RoomId(long id);
}
