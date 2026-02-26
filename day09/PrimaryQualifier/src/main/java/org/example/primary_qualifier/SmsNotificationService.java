package org.example.primary_qualifier;

import org.springframework.stereotype.Component;

// @Component tells Spring to create this bean
// No @Primary, so this is NOT the default choice
@Component
public class SmsNotificationService implements NotificationService {
    
    @Override
    public void sendMsg(String message) {
        System.out.println("SMS: " + message);
    }
}
