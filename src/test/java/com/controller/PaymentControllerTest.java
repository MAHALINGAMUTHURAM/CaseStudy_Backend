package com.controller;

import com.controller.PaymentController;
import com.exception.CustomException;
import com.model.Payment;
import com.model.Reservation;
import com.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment_Success() {
        Payment payment = new Payment();
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);
        payment.setReservation(reservation);
        payment.setAmount(100.0);
        payment.setPaymentStatus("PAID");

        when(paymentService.findByReservation(1L)).thenReturn(Collections.emptyList());

        ResponseEntity<Object> response = paymentController.createPayment(payment);

        assertEquals(201, response.getStatusCodeValue());
        verify(paymentService, times(1)).savePayment(payment);
    }

    @Test
    void testCreatePayment_AlreadyExists() {
        Payment payment = new Payment();
        Reservation reservation = new Reservation();
        reservation.setReservationId(1L);
        payment.setReservation(reservation);

        when(paymentService.findByReservation(1L)).thenReturn(List.of(payment));

        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.createPayment(payment);
        });

        assertEquals("ADDFAILS", exception.getCode());
        verify(paymentService, never()).savePayment(any());
    }
    

    @Test
    void testGetAllPayments_Success() {
        Payment payment = new Payment();
        when(paymentService.getAll()).thenReturn(List.of(payment));

        ResponseEntity<Object> response = paymentController.getAllPayments();

        // Debugging: Print the response if needed
        System.out.println(response);

        assertEquals(200, response.getStatusCodeValue()); // Should pass
        assertNotNull(response.getBody());
    }


    @Test
    void testGetAllPayments_Empty() {
        when(paymentService.getAll()).thenReturn(Collections.emptyList());

        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.getAllPayments();
        });

        assertEquals("GETALLFAILS", exception.getCode());
    }

    @Test
    void testGetPaymentById_Success() {
        Payment payment = new Payment();
        when(paymentService.existsById(1L)).thenReturn(true);
        when(paymentService.getPaymentById(1L)).thenReturn(payment);

        ResponseEntity<Object> response = paymentController.getPaymentById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(payment, response.getBody());
    }

    @Test
    void testGetPaymentById_NotFound() {
        when(paymentService.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.getPaymentById(1L);
        });

        assertEquals("GETFAILS", exception.getCode());
    }

    @Test
    void testGetPaymentsByStatus_Success() {
        Payment payment = new Payment();
        when(paymentService.getByStatus("PAID")).thenReturn(List.of(payment));

        ResponseEntity<Object> response = paymentController.getPaymentsByStatus("PAID");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetPaymentsByStatus_Empty() {
        when(paymentService.getByStatus("PAID")).thenReturn(Collections.emptyList());

        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.getPaymentsByStatus("PAID");
        });

        assertEquals("GETALLFAILS", exception.getCode());
    }

    @Test
    void testGetTotalRevenue() {
        when(paymentService.getTotalRevenue()).thenReturn(1000.0);

        ResponseEntity<Object> response = paymentController.getTotalRevenue();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1000.0, response.getBody());
    }

    @Test
    void testDeletePayment_Success() {
        when(paymentService.existsById(1L)).thenReturn(true);

        ResponseEntity<Object> response = paymentController.deletePayment(1L);

        verify(paymentService, times(1)).deletePayment(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeletePayment_NotFound() {
        when(paymentService.existsById(1L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.deletePayment(1L);
        });

        assertEquals("DLTFAILS", exception.getCode());
    }
}