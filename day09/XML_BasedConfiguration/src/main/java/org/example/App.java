package org.example;

import org.example.services.EmailService;
import org.example.services.MessageService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");

        MessageService messageService = context.getBean(MessageService.class);
        messageService.sendMessage();


        System.out.println("\n--- Checking for Bean Scope ---");
        System.out.println("-- PROTOTYPE --");
        MessageService messageService02 = context.getBean(MessageService.class);
        MessageService messageService03 = context.getBean(MessageService.class);
        System.out.println(messageService);
        System.out.println(messageService02);
        System.out.println(messageService03);

        System.out.println("\n-- PROTOTYPE --");
        EmailService emailService = context.getBean(EmailService.class);
        EmailService emailService02 = context.getBean(EmailService.class);
        EmailService emailService03 = context.getBean(EmailService.class);
        System.out.println(emailService);
        System.out.println(emailService02);
        System.out.println(emailService03);


        ((ClassPathXmlApplicationContext)context).close();

        // if we create ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        // then we can directly do context.close();
        // here we performed down casting
    }
}
