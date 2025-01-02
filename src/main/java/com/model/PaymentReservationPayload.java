package com.model;

public class PaymentReservationPayload {
    private Reservation reservation;
    private Payment payment;

    // Getters and setters for reservation and payment

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
