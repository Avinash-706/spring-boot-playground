package org.example.utility;

import org.example.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    private final NotificationService notificationService;

    @Autowired
    private ResturantService resturantService;

    public OrderService(@Qualifier("smsNotification") NotificationService notificationService){
        this.notificationService = notificationService;
        System.out.println("-- Constructor Injection: NotificationService injected into OrderService --");
    }
    
    public void placeOrder(String orderDetails) {
        System.out.println("\n=== Placing Order ===");
        resturantService.processOrder();
        notificationService.sendNotification("Order placed: " + orderDetails);
        System.out.println("=== Order Completed ===\n");
    }

}
