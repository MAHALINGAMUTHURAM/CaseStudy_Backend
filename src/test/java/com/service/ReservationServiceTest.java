package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dao.ReservationDAO;
import com.model.Reservation;
import com.model.Room;
import com.service.ReservationService;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationDAO reservationDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);

        when(reservationDAO.existsById(1L)).thenReturn(true);

        boolean result = reservationService.findReservation(reservation);

        assertTrue(result);
        verify(reservationDAO, times(1)).existsById(1L);
    }

    @Test
    void testSaveReservation() {
        Reservation reservation = new Reservation();
        reservationService.saveReservation(reservation);

        verify(reservationDAO, times(1)).save(reservation);
    }

    @Test
    void testGetAllReservations() {
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());
        when(reservationDAO.findAll()).thenReturn(reservations);

        List<Reservation> result = reservationService.getAllReservations();

        assertEquals(2, result.size());
        verify(reservationDAO, times(1)).findAll();
    }

    @Test
    void testGetReservationById() {
        Reservation reservation = new Reservation();
        when(reservationDAO.findById(1L)).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.getReservationById(1L);

        assertNotNull(result);
        verify(reservationDAO, times(1)).findById(1L);
    }

    @Test
    void testGetReservationsByDateRange() {
        Date startDate = new Date();
        Date endDate = new Date();
        List<Reservation> reservations = Arrays.asList(new Reservation(), new Reservation());

        when(reservationDAO.findByCheckInDateBetween(startDate, endDate)).thenReturn(reservations);

        List<Reservation> result = reservationService.getReservationsByDateRange(startDate, endDate);

        assertEquals(2, result.size());
        verify(reservationDAO, times(1)).findByCheckInDateBetween(startDate, endDate);
    }

    @Test
    void testExistsById() {
        when(reservationDAO.existsById(1L)).thenReturn(true);

        boolean result = reservationService.existsById(1L);

        assertTrue(result);
        verify(reservationDAO, times(1)).existsById(1L);
    }

    @Test
    void testUpdateReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);

        when(reservationDAO.existsById(1L)).thenReturn(true);

        reservationService.updateReservation(1L, reservation);

        verify(reservationDAO, times(1)).existsById(1L);
        verify(reservationDAO, times(1)).save(reservation);
        assertEquals(1L, reservation.getReservationId());
    }

    @Test
    void testDeleteReservation() {
        reservationService.deleteReservation(1L);

        verify(reservationDAO, times(1)).deleteById(1L);
    }

    @Test
    void testFindByRoom() {
        List<Reservation> rooms = Arrays.asList(new Reservation(), new Reservation());

        when(reservationDAO.findByRoom_RoomId(1L)).thenReturn(rooms);

        List<Reservation> result = reservationService.findByRoom(1L);

        assertEquals(2, result.size());
        verify(reservationDAO, times(1)).findByRoom_RoomId(1L);
    }
}
