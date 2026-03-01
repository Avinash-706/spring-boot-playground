package org.example.entity;

import org.example.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor {
    private final PaymentService paymentService;
    private final PaymentService paymentService02;

    @Autowired
    private TransactionLogger transactionLogger;
    
    // consturtor injection : upI
    public PaymentProcessor(@Qualifier("upiPayment") PaymentService paymentService, @Qualifier("creditCardPayment") PaymentService paymentService02) {
        this.paymentService02 = paymentService02;
        this.paymentService = paymentService;
        System.out.println("PaymentProcessor bean created");
    }
    
    public void processTransaction(double amount) {
        System.out.println("\n--- Processing ---");
        paymentService.processPayment(amount);
        transactionLogger.logTransaction("UPI", amount);
        System.out.println("--- Transaction Complete ---\n");
    }
}
