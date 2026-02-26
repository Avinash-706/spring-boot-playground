package org.example.primary_qualifier;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PrimaryQualifierDemo {
    public static void main(String[] args) {
        
        System.out.println("=== Spring Container Created ===\n");
        
        // Step 1: Create Spring container using annotation-based configuration
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrimaryQualifierConfig.class);
        
        // Step 2: Get NotificationManager bean from Spring container
        NotificationManager manager = context.getBean(NotificationManager.class);
        
        // Step 3: Demonstrate @Primary - uses default service (EmailNotificationService)
        manager.sendPrimaryNotification("Welcome to Spring!");
        
        // Step 4: Demonstrate @Qualifier - uses all specific services
        manager.sendAllNotifications("Important Update!");
        
        // Step 5: Demonstrate @Qualifier - uses specific SMS service
        manager.sendSmsOnly("Your OTP is 123456");
        
        // Step 6: Demonstrate @Qualifier - uses specific Push service
        manager.sendPushOnly("New message received");
        
        // Step 7: Close the Spring container to release resources
        context.close();
        System.out.println("\n=== Spring Container Closed ===");
    }
}
