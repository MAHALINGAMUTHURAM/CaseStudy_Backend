package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ReservationDAO;
import com.model.Reservation;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationDAO reservationDAO;

    public boolean findReservation(Reservation reservation) {
    
        return reservationDAO.existsById(reservation.getReservationId());
    }

    public void saveReservation(Reservation reservation) {
        reservationDAO.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.findAll();
    }

    public Reservation getReservationById(long id) {
        return reservationDAO.findById(id).orElse(null);
    }

    public List<Reservation> getReservationsByDateRange(java.util.Date startDate, java.util.Date endDate) {
        return reservationDAO.findByCheckInDateBetween(startDate, endDate);
    }

    public boolean existsById(long id) {
        return reservationDAO.existsById(id);
    }

    public void updateReservation(long id, Reservation reservation) {
        if (reservationDAO.existsById(id)) {
            reservation.setReservationId(id);
            reservationDAO.save(reservation);
        }
    }

    public void deleteReservation(long id) {
    	
            reservationDAO.deleteById(id);
    }
    
    public List<Reservation> findByRoom(long id)
    {
    	return reservationDAO.findByRoom_RoomId(id);
    }
}
