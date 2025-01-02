package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.dao.PaymentDAO;
import com.dao.ReservationDAO;
import com.model.Payment;
import com.model.Reservation;
import com.service.PaymentService;

class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentDAO paymentDAO;

    @Mock
    private ReservationDAO reservationDAO;

    private Reservation reservation;
    private Payment payment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize mock data
        reservation = new Reservation();
        reservation.setReservationId(1L);
        reservation.setGuest_email("guest@example.com");

        payment = new Payment();
        payment.setPayment_id(1L);
        payment.setAmount(100.0);
        payment.setPaymentStatus("COMPLETED");
        payment.setReservation(reservation);
    }

    @Test
    void testSavePaymentAndReservation() {
        // Arrange: Mock the DAO methods
        when(reservationDAO.save(any(Reservation.class))).thenReturn(reservation);
        when(paymentDAO.save(any(Payment.class))).thenReturn(payment);

        // Act: Call service method
        paymentService.savePaymentAndReservation(reservation, payment);

        // Assert: Verify that save methods were called
        verify(reservationDAO, times(1)).save(reservation);
        verify(paymentDAO, times(1)).save(payment);
    }

    @Test
    void testGetAllPayments() {
        // Arrange: Mock the DAO to return a list of payments
        when(paymentDAO.findAll()).thenReturn(Arrays.asList(payment));

        // Act: Call service method
        List<Payment> result = paymentService.getAll();

        // Assert: Verify the result
        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
    }

    @Test
    void testExistsById() {
        // Arrange: Mock the DAO method to return true for the given ID
        when(paymentDAO.existsById(1L)).thenReturn(true);

        // Act: Call service method
        boolean result = paymentService.existsById(1L);

        // Assert: Verify the result
        assertTrue(result);
    }

    @Test
    void testGetPaymentById() {
        // Arrange: Mock the DAO to return a payment for the given ID
        when(paymentDAO.findById(1L)).thenReturn(Optional.of(payment));

        // Act: Call service method
        Payment result = paymentService.getPaymentById(1L);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
    }

    @Test
    void testGetPaymentById_NotFound() {
        // Arrange: Mock the DAO to return empty for the given ID
        when(paymentDAO.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verify the exception when payment is not found
        assertThrows(NoSuchElementException.class, () -> paymentService.getPaymentById(1L));
    }

    @Test
    void testGetPaymentsByStatus() {
        // Arrange: Mock the DAO to return a list of payments with the given status
        when(paymentDAO.findByPaymentStatus("COMPLETED")).thenReturn(Arrays.asList(payment));

        // Act: Call service method
        List<Payment> result = paymentService.getByStatus("COMPLETED");

        // Assert: Verify the result
        assertEquals(1, result.size());
        assertEquals("COMPLETED", result.get(0).getPaymentStatus());
    }

    @Test
    void testGetTotalRevenue() {
        // Arrange: Mock the DAO to return a list of payments
        when(paymentDAO.findAll()).thenReturn(Arrays.asList(payment));

        // Act: Call service method
        double totalRevenue = paymentService.getTotalRevenue();

        // Assert: Verify the result
        assertEquals(100.0, totalRevenue);
    }

    @Test
    void testDeletePayment() {
        // Arrange: Mock the DAO to return the payment for the given ID
        when(paymentDAO.findById(1L)).thenReturn(Optional.of(payment));

        // Act: Call service method
        paymentService.deletePayment(1L);

        // Assert: Verify that the delete method was called
        verify(paymentDAO, times(1)).delete(payment);
    }

    @Test
    void testDeletePayment_NotFound() {
        // Arrange: Mock the DAO to return empty for the given ID
        when(paymentDAO.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert: Verify the exception when trying to delete a non-existing payment
        assertThrows(NoSuchElementException.class, () -> paymentService.deletePayment(1L));
    }

    @Test
    void testFindByReservation() {
        // Arrange: Mock the DAO to return a list of payments associated with a reservation
        when(paymentDAO.findByReservation_ReservationId(1L)).thenReturn(Arrays.asList(payment));

        // Act: Call service method
        List<Payment> result = paymentService.findByReservation(1L);

        // Assert: Verify the result
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getPayment_id());
    }
}
