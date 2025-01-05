package com.controller;

import com.model.Payment;
import com.model.PaymentReservationPayload;
import com.model.Reservation;
import com.model.UserEntity;
import com.service.PaymentService;
import com.service.UserService;
import com.exception.CustomException;
import com.exception.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    @Mock
    private UserService userService;

    private PaymentReservationPayload payload;
    private Reservation reservation;
    private Payment payment;

    @BeforeEach
    public void setUp() {
        // Initialize the objects before each test
        reservation = new Reservation();
        payment = new Payment();
        payload = new PaymentReservationPayload();
        payload.setReservation(reservation);
        payload.setPayment(payment);
    }

//    @Test
//    public void testCreatePayment_Success() {
//        // Mock the behavior of the services
//        when(paymentService.findByReservation(anyLong())).thenReturn(List.of(new Payment()));
//        when(userService.findUser(anyString())).thenReturn(Optional.of(new UserEntity())); // Mock the User class
//
//        // Call the method
//        ResponseEntity<Object> response = paymentController.createPayment(payload, "user1");
//
//        // Verify the result
//        assertEquals(201, response.getStatusCodeValue());
//        assertTrue(response.getBody() instanceof Response);
//        assertEquals("POSTSUCCESS", ((Response) response.getBody()).getCode());
//        assertEquals("Payment added successfully", ((Response) response.getBody()).getMessage());
//
//        // Verify that the paymentService's save method was called
//        verify(paymentService, times(1)).savePaymentAndReservation(any(), any());
//    }

    @Test
    public void testCreatePayment_Failure_PaymentExists() {
        // Mock the behavior of the services
        when(paymentService.findByReservation(anyLong())).thenReturn(List.of(new Payment()));

        // Call the method and assert exception is thrown
        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.createPayment(payload, "user1");
        });

        assertEquals("ADDFAILS", exception.getCode());
        assertEquals("Payment already exists", exception.getMessage());

        // Verify that the savePaymentAndReservation method is never called
        verify(paymentService, never()).savePaymentAndReservation(any(), any());
    }

    @Test
    public void testGetAllPayments_Success() {
        // Mock the behavior of the services
        when(paymentService.getAll()).thenReturn(List.of(new Payment())); // Return a List<Payment> here

        // Call the method
        ResponseEntity<Object> response = paymentController.getAllPayments();

        // Verify the result
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof List);
        assertFalse(((List) response.getBody()).isEmpty());
    }

    @Test
    public void testGetPaymentById_NotFound() {
        // Mock the behavior of the service
        when(paymentService.existsById(anyLong())).thenReturn(false);

        // Call the method and assert exception is thrown
        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.getPaymentById(1L);
        });

        assertEquals("GETFAILS", exception.getCode());
        assertEquals("Payment doesn't exist", exception.getMessage());
    }

    @Test
    public void testDeletePayment_Success() {
        // Mock the behavior of the service
        when(paymentService.existsById(anyLong())).thenReturn(true);

        // Call the method
        ResponseEntity<Object> response = paymentController.deletePayment(1L);

        // Verify the result
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Response);
        assertEquals("DELETESUCCESS", ((Response) response.getBody()).getCode());
        assertEquals("Payment deleted successfully", ((Response) response.getBody()).getMessage());

        // Verify that deletePayment method was called once
        verify(paymentService, times(1)).deletePayment(1L);
    }

    @Test
    public void testDeletePayment_NotFound() {
        // Mock the behavior of the service
        when(paymentService.existsById(anyLong())).thenReturn(false);

        // Call the method and assert exception is thrown
        CustomException exception = assertThrows(CustomException.class, () -> {
            paymentController.deletePayment(1L);
        });

        assertEquals("DLTFAILS", exception.getCode());
        assertEquals("Payment doesn't exist", exception.getMessage());

        // Verify that deletePayment method is never called
        verify(paymentService, never()).deletePayment(anyLong());
    }
}