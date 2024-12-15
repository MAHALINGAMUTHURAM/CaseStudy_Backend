package com.service;
import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.ReservationDAO;
import com.model.Reservation;
@Service
public class ReservationService {
    @Autowired
    private ReservationDAO reservationDAO;

    public void add(Reservation reservation) {
        reservationDAO.save(reservation);
    }
    public List<Reservation> getAll() {
        return reservationDAO.findAll();
    }
    public Reservation getById(Long id) {
        return reservationDAO.findById(id).orElse(null);
    }
//    public List<Reservation> getByDateRange(Date startDate, Date endDate) {
//        return reservationDAO.findByCheckInDateBetween(startDate, endDate);
//    }
//    public void update(Reservation reservation) {
//        reservationDAO.save(reservation);
//    }//CHECK THIS
    public void delete(Long id) {
        if (reservationDAO.existsById(id)) {
            reservationDAO.deleteById(id);
        }
    }
}
