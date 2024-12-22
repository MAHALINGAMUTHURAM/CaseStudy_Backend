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

    public void savePayment(Payment payment) {
    	
        paymentDAO.save(payment);
    }
    
    public List<Payment> getAll() {
        return paymentDAO.findAll();
    }
    
    public boolean existsById(long id) {
        return paymentDAO.existsById(id);
    }
    
    public Payment getPaymentById(long id)
    {
    	return paymentDAO.findById(id).get();
    }
    
   public List<Payment> getByStatus(String status) {
  	return paymentDAO.findByPaymentStatus(status);
    }
   
	public double getTotalRevenue() {
        List<Payment> payments = paymentDAO.findAll();
        return payments.stream().mapToDouble(Payment::getAmount).sum();
    }

    public void deletePayment(long id) {
    	
    	Payment payment=paymentDAO.findById(id).get();
        paymentDAO.delete(payment);
    }
    
    public List<Payment> findByReservation(long id)
    {
    	return paymentDAO.findByReservation_ReservationId(id);
    }
}
