package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.model.Reservation;

@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Long> {

	List<Reservation> findByCheckInDateBetween(java.util.Date startDate, java.util.Date endDate);
	List<Reservation> findByRoom_RoomId(long id);
}
