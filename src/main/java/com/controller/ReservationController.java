package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.model.Reservation;
import com.service.ReservationService;
import com.exception.CustomException;
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
                throw new CustomException("ADDFAILS", "Reservation already exists");
            }
            reservationService.saveReservation(reservation);
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Reservation added successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Error adding reservation\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllReservations() {
        try {
            List<Reservation> reservations = reservationService.getAllReservations();
            if (reservations.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Reservation list is empty");
            }
            return ResponseEntity.ok(reservations);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETALLFAILS\", \"message\": \"Error retrieving reservations\"}");
        }
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Object> getReservationById(@PathVariable("reservationId") Long reservationId) {
        try {
            Reservation reservation = reservationService.getReservationById(reservationId);
            if (reservation == null) {
                throw new CustomException("GETFAILS", "Reservation doesn't exist");
            }
            return ResponseEntity.ok(reservation);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error retrieving reservation\"}");
        }
    }

    @GetMapping("/date-range/{startDate}/{endDate}")
    public ResponseEntity<Object> getReservationsByDateRange(
    		@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date startDate,
    		@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") java.util.Date endDate) {
        try {
            List<Reservation> reservations = reservationService.getReservationsByDateRange(startDate, endDate);
            if (reservations.isEmpty()) {
                throw new CustomException("GETFAILS", "No reservations found in the specified date range");
            }
            return ResponseEntity.ok(reservations);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error retrieving reservations by date range\"}");
        }
    }

    @PutMapping("/update/{reservationId}")
    public ResponseEntity<Object> updateReservation(@PathVariable("reservationId") Long reservationId, 
                                                     @RequestBody Reservation reservation) {
        try {
            if (!reservationService.findById(reservationId)) {
                throw new CustomException("UPDTFAILS", "Reservation doesn't exist");
            }
            reservationService.updateReservation(reservationId, reservation);
            return ResponseEntity.ok("{\"code\": \"UPDATESUCCESS\", \"message\": \"Reservation updated successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"UPDTFAILS\", \"message\": \"Error updating reservation\"}");
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Object> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        try {
            if (!reservationService.findById(reservationId)) {
                throw new CustomException("DLTFAILS", "Reservation doesn't exist");
            }
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Reservation deleted successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"DLTFAILS\", \"message\": \"Error deleting reservation\"}");
        }
    }
}
