package org.example.entity;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class TransactionLogger {
    
    public TransactionLogger() {
        System.out.println("TransactionLogger bean created");
    }
    
    @PostConstruct
    public void init() {
        System.out.println("Logger initialized");
    }
    
    @PreDestroy
    public void preDestroy() {
        System.out.println("Logger destroyed");
    }
    
    public void logTransaction(String paymentType, double amount) {
        System.out.println("Transaction logged: " + paymentType + " - " + amount);
    }
}
