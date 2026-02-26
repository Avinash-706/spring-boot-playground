package org.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Component tells Spring to create and manage this class as a bean
@Component
public class SetterInjectionService {
    
    // Declare the dependency
    private EmailService emailService;
    
    // Setter injection - Spring calls this method to inject EmailService
    // @Autowired is required for setter injection
    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
        System.out.println("Setter Injection - EmailService injected");
    }
    
    // Method to use the injected service
    public void sendMessage() {
        System.out.println("Setter Injection - Sending Message..");
        emailService.send();
    }
}
