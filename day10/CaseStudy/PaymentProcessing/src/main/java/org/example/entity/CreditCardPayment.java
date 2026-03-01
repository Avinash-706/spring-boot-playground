package org.example.entity;

import org.example.services.PaymentService;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@Lazy
public class CreditCardPayment implements PaymentService {
    
    public CreditCardPayment() {
        System.out.println("CreditCardPayment bean created (Lazy)");
    }
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing Credit Card payment of $" + amount);
    }
}
