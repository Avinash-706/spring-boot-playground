package org.example;

import org.example.entity.AppConfig;
import org.example.entity.PaymentProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        System.out.println("=== Starting Payment Processing System ===\n");
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        PaymentProcessor processor = context.getBean(PaymentProcessor.class);
        processor.processTransaction(150.75);

        //  prototype scope - get another UpiPayment instance
        System.out.println("\n=== Demonstrating Prototype Scope ===");
        PaymentProcessor processor2 = context.getBean(PaymentProcessor.class);
        processor2.processTransaction(200.00);
        
        System.out.println("\n=== Shutting Down ===");
        ((AnnotationConfigApplicationContext) context).close();
        System.out.println("\n=== Application Terminated ===");
    }
}
