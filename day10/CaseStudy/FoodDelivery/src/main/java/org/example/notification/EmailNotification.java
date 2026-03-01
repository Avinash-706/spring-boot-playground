package org.example.notification;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class EmailNotification implements NotificationService {
    {
        System.out.println("Non-Static Block : Emmail Service is launching !!");
    }

    @Override
    public void sendNotification(String message){
        System.out.println("📧 EMAIL: " + message);
    }

}
