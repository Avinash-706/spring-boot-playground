package org.example.services;

public class MessageService {
    private EmailService emailService;

        // constructor injection
 //    public  MessageService(EmailService emailService){
 //        this.emailService = emailService;
 //    }

    {
        System.out.println("-- Starting Message Service --");
    }

    // setter injection
    public void setEmailService(EmailService emailService) {
        System.out.println("-- Setter called --");
        this.emailService = emailService;
    }

    public void sendMessage(){
        System.out.println("Message is Sent !!");
        emailService.sendMail();
        System.out.println("-- Exiting Message Service --");
    }
}
