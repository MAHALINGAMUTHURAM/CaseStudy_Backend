package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.model.Payment;
import com.service.PaymentService;
import com.exception.CustomException;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/post")
    public ResponseEntity<Object> createPayment(@RequestBody Payment payment) {
        try {
            if (!paymentService.add(payment)) {
                throw new CustomException("ADDFAILS", "Payment already exists");
            }
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Payment added successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(400).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Internal error occurred while adding payment\"}");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAll();
            if (payments.isEmpty()) {
                throw new CustomException("GETALLFAILS", "Payment list is empty");
            }
            return ResponseEntity.ok(payments);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching payments\"}");
        }
    }

    @GetMapping("/{payment_id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable("payment_id") Long paymentId) {
        try {
            Payment payment = paymentService.getById(paymentId);
            if (payment == null) {
                throw new CustomException("GETFAILS", "Payment doesn't exist");
            }
            return ResponseEntity.ok(payment);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETFAILS\", \"message\": \"Error fetching payment by ID\"}");
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getPaymentsByStatus(@PathVariable("status") String status) {
        try {
            List<Payment> payments = paymentService.getByStatus(status);
            if (payments.isEmpty()) {
                throw new CustomException("GETALLFAILS", "No payments found with the specified status");
            }
            return ResponseEntity.ok(payments);
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"GETALLFAILS\", \"message\": \"Error fetching payments by status\"}");
        }
    }

    @GetMapping("/total-revenue")
    public ResponseEntity<Object> getTotalRevenue() {
        double totalRevenue = paymentService.getTotalRevenue();
        return ResponseEntity.ok(totalRevenue);
    }

    @DeleteMapping("/{payment_id}")
    public ResponseEntity<Object> deletePayment(@PathVariable("payment_id") Long paymentId) {
        try {
            if (!paymentService.delete(paymentId)) {
                throw new CustomException("DLTFAILS", "Payment doesn't exist");
            }
            paymentService.delete(paymentId);
            return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Payment deleted successfully\"}");
        } catch (CustomException e) {
            return ResponseEntity.status(404).body("{\"code\": \"" + e.getCode() + "\", \"message\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"code\": \"DLTFAILS\", \"message\": \"Error deleting payment\"}");
        }
    }
}
