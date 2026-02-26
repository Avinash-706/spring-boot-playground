package org.example.primary_qualifier;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

// @Component tells Spring to create this bean
// @Primary tells Spring to use this as the default when multiple implementations exist
@Component
@Primary
public class EmailNotificationService implements NotificationService {
    
    @Override
    public void sendMsg(String message) {
        System.out.println("Email: " + message);
    }
}
