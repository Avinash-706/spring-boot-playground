package org.example.entity;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class AuditService {
    
    public AuditService() {
        System.out.println("Creating instance of: " + this.getClass().getSimpleName());
    }
    
    @PostConstruct
    public void init() {
        System.out.println("[PostConstruct] Initializing audit configuration for " + this.getClass().getSimpleName());
    }


    @PreDestroy
    public void destroy() {
        System.out.println("[PreDestroy] Releasing audit resources for " + this.getClass().getSimpleName());
    }

    //log
    public void logBeforeProcessing(String message) {
        System.out.println("[AUDIT LOG] Starting to process document: " + message);
    }
}
