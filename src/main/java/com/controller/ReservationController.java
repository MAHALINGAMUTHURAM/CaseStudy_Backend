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
        try {
            if (reservationService.findReservation(reservation)) {
                return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"Reservation already exists\"}");
            }
            reservationService.saveReservation(reservation);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Reservation added successfully\"}");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Error adding reservation\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            if (reservations.isEmpty()) {
                return ResponseEntity.status(404).body("{\"code\": \"GETALLFAILS\", \"message\": \"Reservation list is empty\"}");
            }
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"GETALLFAILS\", \"message\": \"Error retrieving reservations\"}");
        }
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Object> getReservationById(@PathVariable("reservationId") Long reservationId) {
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation == null) {
                return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Reservation doesn't exist\"}");
            }
            return ResponseEntity.ok(reservation);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error retrieving reservation\"}");
        }
    }

    @GetMapping("/date-range")
    public ResponseEntity<Object> getReservationsByDateRange(
            @RequestParam("startDate") java.util.Date startDate,
            @RequestParam("endDate") java.util.Date endDate) {
        try {
            List<Reservation> reservations = reservationService.getReservationsByDateRange(startDate, endDate);
            if (reservations.isEmpty()) {
                return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"No reservations found in the specified date range\"}");
            }
            return ResponseEntity.ok(reservations);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error retrieving reservations by date range\"}");
        }
    }


    @PutMapping("/update/{reservationId}")
    public ResponseEntity<Object> updateReservation(@PathVariable("reservationId") Long reservationId, 
                                                     @RequestBody Reservation reservation) {
        try {
            if (!reservationService.findById(reservationId)) {
                return ResponseEntity.status(404).body("{\"code\": \"UPDTFAILS\", \"message\": \"Reservation doesn't exist\"}");
            }
            reservationService.updateReservation(reservationId, reservation);
            return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"Reservation updated successfully\"}");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"UPDTFAILS\", \"message\": \"Error updating reservation\"}");
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Object> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        try {
            if (!reservationService.findById(reservationId)) {
                return ResponseEntity.status(404).body("{\"code\": \"DLTFAILS\", \"message\": \"Reservation doesn't exist\"}");
            }
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Reservation deleted successfully\"}");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"DLTFAILS\", \"message\": \"Error deleting reservation\"}");
        }
    }
}
