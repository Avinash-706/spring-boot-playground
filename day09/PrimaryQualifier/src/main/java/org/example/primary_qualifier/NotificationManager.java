package org.example.primary_qualifier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

// @Component tells Spring to create this bean
@Component
public class NotificationManager {
    
    // This will get the @Primary bean (EmailNotificationService)
    private final NotificationService primaryService;
    
    // @Qualifier tells Spring to inject specific beans by name
    private final NotificationService emailService;
    private final NotificationService smsService;
    private final NotificationService pushService;
    
    // Constructor injection with @Qualifier
    public NotificationManager(
            NotificationService primaryService,  // Gets @Primary (EmailNotificationService)
            @Qualifier("emailNotificationService") NotificationService emailService,
            @Qualifier("smsNotificationService") NotificationService smsService,
            @Qualifier("pushNotificationService") NotificationService pushService)
    {
        this.primaryService = primaryService;
        this.emailService = emailService;
        this.smsService = smsService;
        this.pushService = pushService;
        System.out.println("NotificationManager created with all notification services");
    }
    
    // Method using @Primary service (default)
    public void sendPrimaryNotification(String message) {
        System.out.println("\n--- Using @Primary (Default Service) ---");
        primaryService.sendMsg(message);
    }
    
    // Method using specific services with @Qualifier
    public void sendAllNotifications(String message) {
        System.out.println("\n--- Using @Qualifier (All Services) ---");
        emailService.sendMsg(message);
        smsService.sendMsg(message);
        pushService.sendMsg(message);
    }
    
    // Method using only SMS
    public void sendSmsOnly(String message) {
        System.out.println("\n--- Using @Qualifier (SMS Only) ---");
        smsService.sendMsg(message);
    }
    
    // Method using only Push
    public void sendPushOnly(String message) {
        System.out.println("\n--- Using @Qualifier (Push Only) ---");
        pushService.sendMsg(message);
    }
}
