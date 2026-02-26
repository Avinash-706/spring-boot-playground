package org.example.services;

public class EmailService {

    // CASE 1 : Basic of Constructor Injection and Setter Injection(SCOPED)
    // {
    //     System.out.println("-- Starting Mail Service --");
    // }
    //
    // public void sendMail() {
    //     System.out.println("Send Through Mail !!");
    //     System.out.println("-- Exiting Mail Service --");
    // }


    // CASE 2 : Constructor Injection and Destroy and Init in different scopes
    public EmailService() {
        System.out.println("Email constructor called");
    }
    public void init(){
        System.out.println("Init Method Called");
    }
    public void destroy(){
        System.out.println("Destroy Method Called");
    }
    public void sendMail() {
        System.out.println("Send Through Mail !!");
        System.out.println("-- Exiting Mail Service --");
    }
}
