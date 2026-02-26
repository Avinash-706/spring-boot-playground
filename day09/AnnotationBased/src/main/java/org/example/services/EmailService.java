package org.example.services;

import org.springframework.stereotype.Component;

// @Component tells Spring to create and manage this class as a bean
@Component
public class EmailService {
    
    // Simple method to send email
    public void send() {
        System.out.println("Mail Sent Successfully!!");
    }
}
