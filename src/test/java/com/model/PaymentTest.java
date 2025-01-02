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
public class PaymentTest {

    @InjectMocks
    private Payment payment;

    @Mock
    private Reservation reservation;

    private Date paymentDate;

    @BeforeEach
    public void setUp() {
        // Setting up common data for the test cases
        paymentDate = Date.valueOf("2024-12-25");
        payment = new Payment();
    }

    @Test
    public void testSetAndGetPaymentId() {
        payment.setPayment_id(1L);
        assertEquals(1L, payment.getPayment_id());
    }

    @Test
    public void testSetAndGetReservation() {
        payment.setReservation(reservation);
        assertEquals(reservation, payment.getReservation());
    }

    @Test
    public void testSetAndGetAmount() {
        payment.setAmount(200.50);
        assertEquals(200.50, payment.getAmount());
    }

    @Test
    public void testSetAndGetPaymentDate() {
        payment.setPayment_date(paymentDate);
        assertEquals(paymentDate, payment.getPayment_date());
    }

    @Test
    public void testSetAndGetPaymentStatus() {
        payment.setPaymentStatus("Paid");
        assertEquals("Paid", payment.getPaymentStatus());
    }

    @Test
    public void testPaymentDate() {
        payment.setPayment_date(paymentDate);
        
        assertNotNull(payment.getPayment_date());
        assertEquals(paymentDate, payment.getPayment_date());
    }

    @Test
    public void testPaymentStatus() {
        payment.setPaymentStatus("Failed");
        
        assertNotNull(payment.getPaymentStatus());
        assertEquals("Failed", payment.getPaymentStatus());
    }
}
