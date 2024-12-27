package com.controller;

import com.exception.CustomException;
import com.model.Reservation;
import com.model.Room;
import com.service.ReservationService;
import com.exception.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

    @InjectMocks
    private ReservationController reservationController;

    @Mock
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Reservation createMockReservation(Long roomId) {
        Reservation reservation = new Reservation();
        Room room = new Room();
        room.setRoomId(roomId);
        reservation.setRoom(room);
        return reservation;
    }

    @Test
    void testCreateReservationSuccess() throws CustomException {
        Reservation reservation = createMockReservation(1L);
        when(reservationService.findByRoom(1L)).thenReturn(List.of());
        doNothing().when(reservationService).saveReservation(any());

        ResponseEntity<Object> response = reservationController.createReservation(reservation);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Reservation added successfully", ((Response) response.getBody()).getMessage());
        verify(reservationService, times(1)).saveReservation(reservation);
    }

//    @Test
//    void testCreateReservationFailure() {
//        Reservation reservation = createMockReservation(1L);
//        when(reservationService.findByRoom(1L)).thenReturn(List.of(new Reservation()));
//
//        CustomException exception = assertThrows(CustomException.class,
//                () -> reservationController.createReservation(reservation));
//
//        assertEquals("ADDFAILS", exception.getCode());
//        verify(reservationService, times(0)).saveReservation(any());
//    }

    @Test
    void testGetAllReservationsSuccess() throws CustomException {
        Reservation reservation1 = createMockReservation(1L);
        Reservation reservation2 = createMockReservation(2L);
        when(reservationService.getAllReservations()).thenReturn(Arrays.asList(reservation1, reservation2));

        ResponseEntity<Object> response = reservationController.getAllReservations();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, ((List<Reservation>) response.getBody()).size());
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void testGetAllReservationsFailure() {
        when(reservationService.getAllReservations()).thenReturn(List.of());

        CustomException exception = assertThrows(CustomException.class,
                () -> reservationController.getAllReservations());

        assertEquals("GETALLFAILS", exception.getCode());
        verify(reservationService, times(1)).getAllReservations();
    }

    @Test
    void testGetReservationByIdSuccess() throws CustomException {
        Reservation reservation = createMockReservation(1L);
        when(reservationService.getReservationById(1L)).thenReturn(reservation);

        ResponseEntity<Object> response = reservationController.getReservationById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(reservation, response.getBody());
        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    void testGetReservationByIdFailure() {
        when(reservationService.getReservationById(1L)).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class,
                () -> reservationController.getReservationById(1L));

        assertEquals("GETFAILS", exception.getCode());
        verify(reservationService, times(1)).getReservationById(1L);
    }

    @Test
    void testDeleteReservationSuccess() throws CustomException {
        when(reservationService.existsById(1L)).thenReturn(true);
        doNothing().when(reservationService).deleteReservation(1L);

        ResponseEntity<Object> response = reservationController.deleteReservation(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Reservation deleted successfully", ((Response) response.getBody()).getMessage());
        verify(reservationService, times(1)).deleteReservation(1L);
    }

    @Test
    void testDeleteReservationFailure() {
        when(reservationService.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class,
                () -> reservationController.deleteReservation(1L));

        assertEquals("DLTFAILS", exception.getCode());
        verify(reservationService, times(0)).deleteReservation(anyLong());
    }
}
