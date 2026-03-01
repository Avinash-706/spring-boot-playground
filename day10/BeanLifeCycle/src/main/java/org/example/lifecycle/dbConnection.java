package org.example.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class dbConnection {
    public dbConnection() {
        System.out.println("DB Constructor is called !!");
    }

    @PostConstruct
    public  void init(){
        System.out.println("Init Method is called !!");
    }

    public void executeQuery(){
        System.out.println("Operation Successfully : Query is being Executed !!");
        System.out.println("SELECT * FROM students");
    }

    @PreDestroy
    public  void  destroy(){
        System.out.println("Destroy method called : before Object Destruction");
    }
}
