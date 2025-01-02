package com.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import com.model.Payment;
import com.service.PaymentService; // Assuming you have a service that uses the DAO

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAOTest {

    @Mock
    private PaymentDAO paymentDAO;

    @InjectMocks
    private PaymentService paymentService; // Assuming you have a service that uses the DAO

    private Payment payment;
    private final String paymentStatus = "Completed"; // Dynamic value for payment status
    private final long reservationId = 1L; // Dynamic value for reservation ID

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Initialize Payment object
        payment = new Payment();
        payment.setPayment_id(1L); // Assuming Payment has an ID field
        payment.setPaymentStatus(paymentStatus); // Set status dynamically
    }

    @Test
    public void testFindByPaymentStatus() {
        // Given
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        
        when(paymentDAO.findByPaymentStatus(paymentStatus)).thenReturn(payments);

        // When
        List<Payment> foundPayments = paymentDAO.findByPaymentStatus(paymentStatus);

        // Then
        assertThat(foundPayments).isNotEmpty();
        assertThat(foundPayments.get(0).getPaymentStatus()).isEqualTo(paymentStatus);

        // Verify that the method was called once
        verify(paymentDAO, times(1)).findByPaymentStatus(paymentStatus);
    }

    @Test
    public void testFindByPaymentStatus_NoResults() {
        // Given
        when(paymentDAO.findByPaymentStatus(paymentStatus)).thenReturn(new ArrayList<>());

        // When
        List<Payment> foundPayments = paymentDAO.findByPaymentStatus(paymentStatus);

        // Then
        assertThat(foundPayments).isEmpty();

        // Verify that the method was called once
        verify(paymentDAO, times(1)).findByPaymentStatus(paymentStatus);
    }

    @Test
    public void testFindByReservation_ReservationId() {
        // Given
        List<Payment> payments = new ArrayList<>();
        payments.add(payment);
        
        when(paymentDAO.findByReservation_ReservationId(reservationId)).thenReturn(payments);

        // When
        List<Payment> foundPayments = paymentDAO.findByReservation_ReservationId(reservationId);

        // Then
        assertThat(foundPayments).isNotEmpty();
        assertThat(foundPayments.get(0).getPayment_id()).isEqualTo(payment.getPayment_id());

        // Verify that the method was called once
        verify(paymentDAO, times(1)).findByReservation_ReservationId(reservationId);
    }

    @Test
    public void testFindByReservation_ReservationId_NoResults() {
        // Given
        when(paymentDAO.findByReservation_ReservationId(reservationId)).thenReturn(new ArrayList<>());

        // When
        List<Payment> foundPayments = paymentDAO.findByReservation_ReservationId(reservationId);

        // Then
        assertThat(foundPayments).isEmpty();

        // Verify that the method was called once
        verify(paymentDAO, times(1)).findByReservation_ReservationId(reservationId);
    }
}
