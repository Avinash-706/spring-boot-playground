package org.example;

import org.example.services.AppConfig;
import org.example.services.ConstructorInjectionService;
import org.example.services.FieldInjectionService;
import org.example.services.MessageService;
import org.example.services.NotificationService;
import org.example.services.SetterInjectionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Step 1: Create Spring container using annotation-based configuration
        // We pass AppConfig.class to tell Spring where to find configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        System.out.println("=== Spring Container Created ===\n");
        
        // Step 2: Get beans from Spring container and use them
        
        // Example 1: MessageService (Constructor Injection with multiple dependencies)
        System.out.println("--- Example 1: MessageService ---");
        MessageService messageService = context.getBean(MessageService.class);
        messageService.sendMessage();
        
        System.out.println("\n--- Example 2: Constructor Injection ---");
        // Example 2: Constructor Injection
        ConstructorInjectionService constructorService = context.getBean(ConstructorInjectionService.class);
        constructorService.sendMessage();
        
        System.out.println("\n--- Example 3: Setter Injection ---");
        // Example 3: Setter Injection
        SetterInjectionService setterService = context.getBean(SetterInjectionService.class);
        setterService.sendMessage();
        
        System.out.println("\n--- Example 4: Field Injection ---");
        // Example 4: Field Injection
        FieldInjectionService fieldService = context.getBean(FieldInjectionService.class);
        fieldService.sendMessage();
        
        System.out.println("\n--- Example 5: NotificationService ---");
        // Example 5: NotificationService with multiple methods
        NotificationService notificationService = context.getBean(NotificationService.class);
        notificationService.sendEmailNotification();
        System.out.println();
        notificationService.sendSmsNotification();
        System.out.println();
        notificationService.sendAllNotifications();
        
        // Step 3: Close the Spring container to release resources
        ((AnnotationConfigApplicationContext) context).close();
        System.out.println("\n=== Spring Container Closed ===");
    }
}
