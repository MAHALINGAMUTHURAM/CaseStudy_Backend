package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Reservation;
import com.service.ReservationService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOTest {

    @Mock
    private ReservationDAO reservationDAO;

    @InjectMocks
    private ReservationService reservationService; // Assuming you have a service that uses the DAO

    private Reservation reservation;
    private final long roomId = 1L; // Dynamic value for room ID
    private final Date startDate = Date.valueOf("2024-01-01"); // Dynamic value for start date
    private final Date endDate = Date.valueOf("2024-01-05"); // Dynamic value for end date

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Reservation object
        reservation = new Reservation();
        reservation.setReservationId(1L); // Assuming Reservation has an ID field
        reservation.setGuest_name("John Doe");
        reservation.setGuest_email("john.doe@example.com");
        reservation.setGuest_phone("1234567890");
        reservation.setCheckInDate(startDate); // Set check-in date dynamically
        reservation.setCheckOutDate(endDate); // Set check-out date dynamically
        // Set other properties as needed
    }

    @Test
    public void testFindByCheckInDateBetween() {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        
        when(reservationDAO.findByCheckInDateBetween(startDate, endDate)).thenReturn(reservations);

        // When
        List<Reservation> foundReservations = reservationDAO.findByCheckInDateBetween(startDate, endDate);

        // Then
        assertThat(foundReservations).isNotEmpty();
        assertThat(foundReservations.get(0).getCheckInDate()).isEqualTo(startDate);

        // Verify that the method was called once
        verify(reservationDAO, times(1)).findByCheckInDateBetween(startDate, endDate);
    }

    @Test
    public void testFindByCheckInDateBetween_NoResults() {
        // Given
        when(reservationDAO.findByCheckInDateBetween(startDate, endDate)).thenReturn(new ArrayList<>());

        // When
        List<Reservation> foundReservations = reservationDAO.findByCheckInDateBetween(startDate, endDate);

        // Then
        assertThat(foundReservations).isEmpty();

        // Verify that the method was called once
        verify(reservationDAO, times(1)).findByCheckInDateBetween(startDate, endDate);
    }

    @Test
    public void testFindByRoom_RoomId() {
        // Given
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        
        when(reservationDAO.findByRoom_RoomId(roomId)).thenReturn(reservations);

        // When
        List<Reservation> foundReservations = reservationDAO.findByRoom_RoomId(roomId);

        // Then
        assertThat(foundReservations).isNotEmpty();
        assertThat(foundReservations.get(0).getReservationId()).isEqualTo(reservation.getReservationId());

        // Verify that the method was called once
        verify(reservationDAO, times(1)).findByRoom_RoomId(roomId);
    }

    @Test
    public void testFindByRoom_RoomId_NoResults() {
        // Given
        when(reservationDAO.findByRoom_RoomId(roomId)).thenReturn(new ArrayList<>());

        // When
        List<Reservation> foundReservations = reservationDAO.findByRoom_RoomId(roomId);

        // Then
        assertThat(foundReservations).isEmpty();

        // Verify that the method was called once
        verify(reservationDAO, times(1)).findByRoom_RoomId(roomId);
    }
}
