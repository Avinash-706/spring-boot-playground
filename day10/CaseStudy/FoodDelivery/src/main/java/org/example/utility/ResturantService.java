package org.example.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResturantService {

    private DeliveryService deliveryService;

    @Autowired
    public void setDeliveryService(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
        System.out.println("-- Setter Injection: DeliveryService injected into RestaurantService --");
    }
    
    public void processOrder() {
        System.out.println("Restaurant is processing the order...");
    }
}
