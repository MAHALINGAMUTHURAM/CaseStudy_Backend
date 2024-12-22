package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.model.Payment;
import com.service.PaymentService;
import com.exception.CustomException;
import com.exception.Response;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/post")
    public ResponseEntity<Object> createPayment(@RequestBody Payment payment) {
    	
            if (!paymentService.findByReservation(payment.getReservation().getReservationId()).isEmpty()) {
                throw new CustomException("ADDFAILS", "Payment already exists");
            }
            paymentService.savePayment(payment);
            return ResponseEntity.status(201).body(new Response("POSTSUCCESS","Payment added successfully"));
            
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPayments() {

            List<Payment> payments = paymentService.getAll();
            if (payments.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Payment list is empty");
            }
            return ResponseEntity.ok(payments);
       
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable("payment_id") Long paymentId) {

            if (!paymentService.existsById(paymentId)) {
                throw new CustomException("GETFAILS", "Payment doesn't exist");
            }
            return ResponseEntity.ok(paymentService.getPaymentById(paymentId));

    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getPaymentsByStatus(@PathVariable("status") String status) {

            List<Payment> payments = paymentService.getByStatus(status);
            if (payments.isEmpty()) {
                throw new CustomException("GETALLFAILS", "No payments found with the specified status");
            }
            return ResponseEntity.ok(payments);

    }

    @GetMapping("/total-revenue")
    public ResponseEntity<Object> getTotalRevenue() {
    	
        double totalRevenue = paymentService.getTotalRevenue();
        return ResponseEntity.ok(totalRevenue);
    }

    @DeleteMapping("/{payment_id}")
    public ResponseEntity<Object> deletePayment(@PathVariable("payment_id") Long paymentId) {

            if (!paymentService.existsById(paymentId)) {
                throw new CustomException("DLTFAILS", "Payment doesn't exist");
            }
            paymentService.deletePayment(paymentId);
            return ResponseEntity.ok(new Response("DELETESUCCESS","Payment deleted successfully"));


    }
}
