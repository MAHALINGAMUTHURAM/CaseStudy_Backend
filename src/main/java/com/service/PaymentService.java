package com.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dao.PaymentDAO;
import com.model.Payment;
@Service
public class PaymentService {
    @Autowired
    PaymentDAO paymentDAO;

    public void add(Payment payment) {
        paymentDAO.save(payment);
    }
    public List<Payment> getAll() {
        return paymentDAO.findAll();
    }
    public Payment getById(Long id) {
        return paymentDAO.findById(id).orElse(null);
    }
//    public List<Payment> getByStatus(String status) {
//    	return paymentDAO.findByStatus(status);
//    }
	public double getTotalRevenue() {
        List<Payment> payments = paymentDAO.findAll();
        return payments.stream().mapToDouble(Payment::getAmount).sum();
    }

    public void delete(Long id) {
        Payment payment = paymentDAO.findById(id).orElse(null);
        if (payment != null) {
            paymentDAO.delete(payment);
        }
    }
}
