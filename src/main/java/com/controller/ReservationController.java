package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.model.Reservation;
import com.service.ReservationService;
import com.exception.CustomException;
import com.exception.Response;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/post")
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) throws CustomException{

            if (!reservationService.findByRoom(reservation.getRoom().getRoomId()).isEmpty()) {
                throw new CustomException("ADDFAILS", "Reservation already exists");
            }
            reservationService.saveReservation(reservation);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","Reservation added successfully"));

    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllReservations() throws CustomException{

            List<Reservation> reservations = reservationService.getAllReservations();
            if (reservations.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Reservation list is empty");
            }
            return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Object> getReservationById(@PathVariable("reservationId") Long reservationId) throws CustomException {

            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation == null) {
                throw new CustomException("GETFAILS", "Reservation doesn't exist");
            }
            return ResponseEntity.ok(reservation);
    }

    @GetMapping("/date-range/{startDate}/{endDate}")
    public ResponseEntity<Object> getReservationsByDateRange(
    		@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date startDate,
    		@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date endDate) throws CustomException{

            List<Reservation> reservations = reservationService.getReservationsByDateRange(startDate, endDate);
            if (reservations.isEmpty()) {
                throw new CustomException("GETFAILS", "No reservations found in the specified date range");
            }
            return ResponseEntity.ok(reservations);

    }

    @PutMapping("/update/{reservationId}")
    public ResponseEntity<Object> updateReservation(@PathVariable("reservationId") Long reservationId, 
                                                     @RequestBody Reservation reservation) throws CustomException{

            if (!reservationService.existsById(reservationId)) {
                throw new CustomException("UPDTFAILS", "Reservation doesn't exist");
            }
            reservationService.updateReservation(reservationId, reservation);
            return ResponseEntity.ok(new Response("UPDATESUCCESS","Reservation updated successfully"));


    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Object> deleteReservation(@PathVariable("reservationId") Long reservationId) throws CustomException{

            if (!reservationService.existsById(reservationId)) {
                throw new CustomException("DLTFAILS", "Reservation doesn't exist");
            }
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.ok(new Response("DELETESUCCESS","Reservation deleted successfully"));

    }
}
