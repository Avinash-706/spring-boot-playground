package org.example;

public class OrderService {
    private PaymentService paymentservice;

    // Constructor Injection
    public OrderService(PaymentService paymentservice){
        this.paymentservice = paymentservice;
    }

    // Method to test
    public String placeOrder(double amount) {
        System.out.println("[OrderService] Placing Order...");

        boolean paymentSuccess = paymentservice.processPayment(amount);
        if (paymentSuccess) {
            return "ORDER PLACED";
        }
        return "PAYMENT FAILED";
    }

    public boolean validateAndPlaceOrder(double amount) {
        if(amount < 0) {
            return false;
        }
        return paymentservice.processPayment(amount);
    }

}
