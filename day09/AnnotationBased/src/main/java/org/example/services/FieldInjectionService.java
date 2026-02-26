package org.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInjectionService {
    @Autowired
    private EmailService emailService;

    public void sendMessage(){
        System.out.println("Field Injection - Sending Message..");
        emailService.send();
    }
}
