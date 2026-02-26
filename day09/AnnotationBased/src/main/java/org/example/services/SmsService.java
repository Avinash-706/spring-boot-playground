package org.example.services;

import org.springframework.stereotype.Component;

// @Component tells Spring to create and manage this class as a bean
@Component
public class SmsService {
    
    // Simple method to send SMS
    public void send() {
        System.out.println("SMS Sent Successfully!!");
    }
}
