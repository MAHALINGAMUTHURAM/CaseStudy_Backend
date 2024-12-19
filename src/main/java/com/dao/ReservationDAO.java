package com.dao;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.model.Reservation;
@Repository
public interface ReservationDAO extends JpaRepository<Reservation, Integer> {
	
	 List<Reservation> findByCheckInDateBetween(Date startDate, Date endDate);
}
