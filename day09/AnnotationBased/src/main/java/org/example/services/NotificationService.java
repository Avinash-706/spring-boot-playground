package org.example.services;

import org.springframework.stereotype.Component;

// @Component tells Spring to create and manage this class as a bean
@Component
public class NotificationService {
    
    // Declare dependencies as final (best practice)
    private final EmailService emailService;
    private final SmsService smsService;
    
    // Constructor injection - Spring automatically injects both services
    public NotificationService(EmailService emailService, SmsService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
        System.out.println("NotificationService created with EmailService and SmsService");
    }
    
    // Method to send notification via email
    public void sendEmailNotification() {
        System.out.println("--- Sending Email Notification ---");
        emailService.send();
    }
    
    // Method to send notification via SMS
    public void sendSmsNotification() {
        System.out.println("--- Sending SMS Notification ---");
        smsService.send();
    }
    
    // Method to send notification via both email and SMS
    public void sendAllNotifications() {
        System.out.println("--- Sending All Notifications ---");
        emailService.send();
        smsService.send();
        System.out.println("All notifications sent!");
    }
}
