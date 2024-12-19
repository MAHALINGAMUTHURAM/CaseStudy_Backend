package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.model.Reservation;
import com.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/post")
    public ResponseEntity<Object> createReservation(@RequestBody Reservation reservation) {
        if (reservationService.findReservation(reservation)) {
            return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"Reservation already exists\"}");
        }
        reservationService.saveReservation(reservation);
        return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Reservation added successfully\"}");
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        if (reservations.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETALLFAILS\", \"message\": \"Reservation list is empty\"}");
        }
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Object> getReservationById(@PathVariable("reservationId") int reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation == null) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Reservation doesn't exist\"}");
        }
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/date-range")
    public ResponseEntity<Object> getReservationsByDateRange(@RequestParam("startDate") String startDate, 
                                                              @RequestParam("endDate") String endDate) {
        List<Reservation> reservations = reservationService.getReservationsByDateRange(startDate, endDate);
        if (reservations.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"No reservations found in the specified date range\"}");
        }
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/update/{reservationId}")
    public ResponseEntity<Object> updateReservation(@PathVariable("reservationId") int reservationId, 
                                                     @RequestBody Reservation reservation) {
        if (!reservationService.findById(reservationId)) {
            return ResponseEntity.status(404).body("{\"code\": \"UPDTFAILS\", \"message\": \"Reservation doesn't exist\"}");
        }
        reservationService.updateReservation(reservationId, reservation);
        return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"Reservation updated successfully\"}");
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Object> deleteReservation(@PathVariable("reservationId") int reservationId) {
        if (!reservationService.findById(reservationId)) {
            return ResponseEntity.status(404).body("{\"code\": \"DLTFAILS\", \"message\": \"Reservation doesn't exist\"}");
        }
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Reservation deleted successfully\"}");
    }
}
