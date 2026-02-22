package org.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // ApplicationContext is the Spring IoC container
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // Get Bean from container - Spring manages its lifecycle
        MessageService service = context.getBean(MessageService.class);
        System.out.println(service.getMessage());
    }
}
