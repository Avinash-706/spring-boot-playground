package org.example;

import org.example.notification.NotificationService;
import org.example.utility.AppConfig;
import org.example.utility.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println("--- Online Food Delivery System ----");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // Test 1: Default Bean Resolution (@Primary)
        System.out.println("\nTEST 1: Default Bean Resolution (@Primary)");
        NotificationService defaultNotification = context.getBean(NotificationService.class);
        defaultNotification.sendNotification("Testing default notification (should be Email)");
        System.out.println();
        
        // Test 2: Explicit Bean Resolution by Name
        System.out.println("TEST 2: Explicit Bean Resolution by Name");
        NotificationService emailNotification = context.getBean("emailNotification", NotificationService.class);
        emailNotification.sendNotification("Explicitly requesting Email notification");
        System.out.println();
        
        // Test 3: @Lazy Bean - SMS Notification (loaded on first use)
        System.out.println("TEST 3: @Lazy Bean Resolution");
        System.out.println("Requesting SMS Notification (Lazy-loaded)...");
        NotificationService smsNotification = context.getBean("smsNotification", NotificationService.class);
        smsNotification.sendNotification("SMS notification loaded lazily");
        System.out.println();
        
        // Test 4: OrderService with @Qualifier override
        System.out.println("TEST 4: OrderService with @Qualifier Override");
        System.out.println("OrderService uses @Qualifier(\"smsNotification\") to override @Primary");
        OrderService orderService = context.getBean(OrderService.class);
        orderService.placeOrder("Pizza Margherita x2, Coke x1");
        
        System.out.println("\nClosing Application Context...\n");
        
        // Close context to trigger @PreDestroy
        ((AnnotationConfigApplicationContext) context).close();
        
        System.out.println("\nApplication Shutdown Complete!");
    }
}
