package com.controller;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
 
import com.exception.CustomException;
import com.model.Payment;
import com.model.Reservation;
import com.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
 
import java.util.Collections;
 
public class PaymentControllerTest {
 
    @Autowired
    private MockMvc mockMvc;
 
    @Mock
    private PaymentService paymentService;
 
    @InjectMocks
    private PaymentController paymentController;
 
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
    }
 
    @Test
    public void testCreatePayment_Success() throws Exception {
        // Given
        long reservationId = 1L; // Set reservation ID
        double paymentAmount = 100.0; // Set payment amount
 
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
 
        Payment payment = new Payment();
        payment.setAmount(paymentAmount); // Set payment amount
 
        // Mocking service behavior
        when(paymentService.findByReservation(reservationId)).thenReturn(Collections.emptyList());
        doNothing().when(paymentService).savePaymentAndReservation(any(Reservation.class), any(Payment.class));
 
        // When & Then
        mockMvc.perform(post("/api/payment/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"reservation\":{\"reservationId\":" + reservationId + "},\"payment\":{\"amount\":" + paymentAmount + "}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Payment added successfully"));
        
        verify(paymentService, times(1)).savePaymentAndReservation(any(Reservation.class), any(Payment.class));
    }
 
 
   
 
    @Test
    public void testGetAllPayments_Success() throws Exception {
        // Given
        when(paymentService.getAll()).thenReturn(Collections.singletonList(new Payment()));
 
        // When & Then
        mockMvc.perform(get("/api/payment/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        verify(paymentService, times(1)).getAll();
    }
 
   
 
    @Test
    public void testGetPaymentById_Success() throws Exception {
        // Given
        long paymentId = 1L;
        when(paymentService.existsById(paymentId)).thenReturn(true);
        when(paymentService.getPaymentById(paymentId)).thenReturn(new Payment());
 
        // When & Then
        mockMvc.perform(get("/api/payment/{payment_id}", paymentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        verify(paymentService, times(1)).existsById(paymentId);
    }
 
    
 
 
 
    @Test
    public void testGetPaymentsByStatus_Success() throws Exception {
       // Given
       String status = "Completed";
       when(paymentService.getByStatus(status)).thenReturn(Collections.singletonList(new Payment()));
 
       // When & Then
       mockMvc.perform(get("/api/payment/status/{status}", status))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
 
       verify(paymentService, times(1)).getByStatus(status);
   }
 
   
 
   @Test
   public void testGetTotalRevenue() throws Exception {
       // Given
       double totalRevenue = 1000.00;
       when(paymentService.getTotalRevenue()).thenReturn(totalRevenue);
 
       // When & Then
       mockMvc.perform(get("/api/payment/total-revenue"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));
 
       verify(paymentService, times(1)).getTotalRevenue();
   }
 
   @Test
   public void testDeletePayment_Success() throws Exception {
       // Given
       long paymentId = 1L;
       when(paymentService.existsById(paymentId)).thenReturn(true);
 
       // When & Then
       mockMvc.perform(delete("/api/payment/{payment_id}", paymentId))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message").value("Payment deleted successfully"));
 
       verify(paymentService, times(1)).deletePayment(paymentId);
   }
 
  
}