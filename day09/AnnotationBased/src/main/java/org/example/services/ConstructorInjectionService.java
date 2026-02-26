package org.example.services;

import org.springframework.stereotype.Component;

// @Component tells Spring to create and manage this class as a bean
@Component
public class ConstructorInjectionService {
    
    // Declare the dependency
    private final EmailService emailService;
    
    // Constructor injection - Spring automatically injects EmailService here
    // No @Autowired needed in modern Spring (optional for single constructor)
    public ConstructorInjectionService(EmailService emailService) {
        this.emailService = emailService;
        System.out.println("Constructor Injection - EmailService injected");
    }
    
    // Method to use the injected service
    public void sendMessage() {
        System.out.println("Constructor Injection - Sending Message..");
        emailService.send();
    }
}
