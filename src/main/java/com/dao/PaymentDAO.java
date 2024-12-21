package com.dao;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.model.Payment;
import com.model.Reservation;
@Repository
public interface PaymentDAO extends JpaRepository<Payment, Long> {

	   List<Payment> findByPaymentStatus(String status);
	   List<Reservation> findByReservation_ReservationId(long id);
}
