package com.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {

    @InjectMocks
    private Reservation reservation;

    @Mock
    private Room room;

    private Date checkInDate;
    private Date checkOutDate;

    @BeforeEach
    public void setUp() {
        checkInDate = Date.valueOf("2024-12-25");
        checkOutDate = Date.valueOf("2024-12-30");
        reservation = new Reservation();
    }

    @Test
    public void testSetAndGetReservationId() {
        reservation.setReservationId(1L);
        assertEquals(1L, reservation.getReservationId());
    }

    @Test
    public void testSetAndGetGuestName() {
        reservation.setGuest_name("John Doe");
        assertEquals("John Doe", reservation.getGuest_name());
    }

    @Test
    public void testSetAndGetGuestEmail() {
        reservation.setGuest_email("john.doe@example.com");
        assertEquals("john.doe@example.com", reservation.getGuest_email());
    }

    @Test
    public void testSetAndGetGuestPhone() {
        reservation.setGuest_phone("1234567890");
        assertEquals("1234567890", reservation.getGuest_phone());
    }

    @Test
    public void testSetAndGetCheckInDate() {
        reservation.setCheckInDate(checkInDate);
        assertEquals(checkInDate, reservation.getCheckInDate());
    }

    @Test
    public void testSetAndGetCheckOutDate() {
        reservation.setCheckOutDate(checkOutDate);
        assertEquals(checkOutDate, reservation.getCheckOutDate());
    }

    @Test
    public void testSetAndGetRoom() {
        reservation.setRoom(room);
        assertEquals(room, reservation.getRoom());
    }

    @Test
    public void testReservationDates() {
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        
        assertNotNull(reservation.getCheckInDate());
        assertNotNull(reservation.getCheckOutDate());
        assertTrue(reservation.getCheckOutDate().after(reservation.getCheckInDate()));
    }
}
