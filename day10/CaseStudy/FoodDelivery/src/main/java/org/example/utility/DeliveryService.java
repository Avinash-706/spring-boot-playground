package org.example.utility;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class DeliveryService {
    public DeliveryService(){
        System.out.println("-- Delivery Service Activated --");
    }

    @PostConstruct
    public void init(){
        System.out.println("Init Method: Delivery Service Ready");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("Delivery Service Closed");
    }
}
