package com.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.dao.PaymentDAO;
import com.model.Payment;
import com.model.Reservation;
import com.service.PaymentService;

class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentDAO paymentDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePayment() {
        Payment payment = new Payment();
        paymentService.savePayment(payment);
        verify(paymentDAO, times(1)).save(payment);
    }

    @Test
    void testGetAll() {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment());
        payments.add(new Payment());

        when(paymentDAO.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAll();

        assertEquals(2, result.size());
        verify(paymentDAO, times(1)).findAll();
    }

    @Test
    void testExistsById() {
        long id = 1L;
        when(paymentDAO.existsById(id)).thenReturn(true);

        boolean exists = paymentService.existsById(id);

        assertTrue(exists);
        verify(paymentDAO, times(1)).existsById(id);
    }

    @Test
    void testGetPaymentById() {
        long id = 1L;
        Payment payment = new Payment();
        when(paymentDAO.findById(id)).thenReturn(Optional.of(payment));

        Payment result = paymentService.getPaymentById(id);

        assertNotNull(result);
        verify(paymentDAO, times(1)).findById(id);
    }

    @Test
    void testGetByStatus() {
        String status = "PAID";
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment());

        when(paymentDAO.findByPaymentStatus(status)).thenReturn(payments);

        List<Payment> result = paymentService.getByStatus(status);

        assertEquals(1, result.size());
        verify(paymentDAO, times(1)).findByPaymentStatus(status);
    }

    @Test
    void testGetTotalRevenue() {
        List<Payment> payments = new ArrayList<>();
        Payment payment1 = new Payment();
        payment1.setAmount(100.0);
        Payment payment2 = new Payment();
        payment2.setAmount(200.0);

        payments.add(payment1);
        payments.add(payment2);

        when(paymentDAO.findAll()).thenReturn(payments);

        double revenue = paymentService.getTotalRevenue();

        assertEquals(300.0, revenue, 0.01);
        verify(paymentDAO, times(1)).findAll();
    }

    @Test
    void testDeletePayment() {
        long id = 1L;
        Payment payment = new Payment();

        when(paymentDAO.findById(id)).thenReturn(Optional.of(payment));

        paymentService.deletePayment(id);

        verify(paymentDAO, times(1)).findById(id);
        verify(paymentDAO, times(1)).delete(payment);
    }

    @Test
    void testFindByReservation() {
        long reservationId = 1L;
        List<Payment> reservations = new ArrayList<>();
        reservations.add(new Payment());

        when(paymentDAO.findByReservation_ReservationId(reservationId)).thenReturn(reservations);

        List<Payment> result = paymentService.findByReservation(reservationId);

        assertEquals(1, result.size());
        verify(paymentDAO, times(1)).findByReservation_ReservationId(reservationId);
    }
}
