package com.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.model.Payment;
import com.service.PaymentService;
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
                return ResponseEntity.badRequest().body("{\"code\": \"ADDFAILS\", \"message\": \"Payment already exists\"}");
            }
            
            return ResponseEntity.ok("{\"code\": \"POSTSUCCESS\", \"message\": \"Payment added successfully\"}");
            
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body("{\"code\": \"ADDFAILS\", \"message\": \"Payment already exists\"}");
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Object> getAllPayments() {
        List<Payment> payments = paymentService.getAll();
        if (payments.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETALLFAILS\", \"message\": \"Payment list is empty\"}");
        }
        return ResponseEntity.ok(payments);
    }
    @GetMapping("/{payment_id}")
    public ResponseEntity<Object> getPaymentById(@PathVariable("payment_id") Long paymentId) {
        Payment payment = paymentService.getById(paymentId);
        if (payment == null) {
            return ResponseEntity.status(404).body("{\"code\": \"GETFAILS\", \"message\": \"Payment doesn't exist\"}");
        }
        return ResponseEntity.ok(payment);
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getPaymentsByStatus(@PathVariable("status") String status) {
        List<Payment> payments = paymentService.getByStatus(status);
        if (payments.isEmpty()) {
            return ResponseEntity.status(404).body("{\"code\": \"GETALLFAILS\", \"message\": \"No payments found with the specified status\"}");
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
        if (!paymentService.delete(paymentId)) {
            return ResponseEntity.status(404).body("{\"code\": \"DLTFAILS\", \"message\": \"Payment doesn't exist\"}");
        }
        paymentService.delete(paymentId);
        return ResponseEntity.ok("{\"code\": \"DELETESUCCESS\", \"message\": \"Payment deleted successfully\"}");
    }
    
}
