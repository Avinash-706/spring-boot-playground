package org.example.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class AuditService {
    @PostConstruct
    public void init(){
        System.out.println("AuditService initialized");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("AuditService destroyed");
    }

    public void logLoanRequest(double amount, String validator) {
        System.out.println("AUDIT: Loan request for $" + amount + " using " + validator);
    }
}
