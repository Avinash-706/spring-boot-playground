package org.example.services;

import org.springframework.stereotype.Component;

// @Component tells Spring to create and manage this class as a bean
@Component
public class MessageService {
    
    // Declare dependencies as final (best practice for constructor injection)
    private final EmailService emailService;
    private final SmsService smsService;

    // Constructor injection - Spring automatically injects both services
    // No @Autowired needed for single constructor (Spring does it automatically)
    public MessageService(EmailService emailService, SmsService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
        System.out.println("MessageService created with EmailService and SmsService");
    }

    // Method to use the injected services
    public void sendMessage() {
        System.out.println("Sending Message..");
        emailService.send();
        smsService.send();
    }
}
