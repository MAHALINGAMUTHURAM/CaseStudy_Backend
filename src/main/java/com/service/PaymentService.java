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

    public boolean add(Payment payment) {
    	
    	if(!paymentDAO.existsById(payment.getPayment_id())) {
        paymentDAO.save(payment);
        return true;
    }
    	return false;
    }
    public List<Payment> getAll() {
        return paymentDAO.findAll();
    }
    public Payment getById(long id) {
        return paymentDAO.findById(id).orElse(null);
    }
   public List<Payment> getByStatus(String status) {
  	return paymentDAO.findByPaymentStatus(status);
    }
	public double getTotalRevenue() {
        List<Payment> payments = paymentDAO.findAll();
        return payments.stream().mapToDouble(Payment::getAmount).sum();
    }

    public boolean delete(long id) {
        Payment payment = paymentDAO.findById(id).orElse(null);
        if (payment != null) {
            paymentDAO.delete(payment);
            
            return true;
        }
        return false;
    }
}
