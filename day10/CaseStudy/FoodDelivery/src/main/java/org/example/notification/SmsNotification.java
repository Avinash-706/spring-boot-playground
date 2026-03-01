package org.example.notification;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class SmsNotification implements NotificationService {

    static{
        System.out.println("Non-Static Block : Sms Service is launching !!");
    }

    @Override
    public void sendNotification(String message){
        System.out.println("📱 SMS: " + message);
    }
}
