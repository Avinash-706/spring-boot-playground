package org.example.lazy;

import org.springframework.stereotype.Component;

@Component
public class EagerBean {
    public EagerBean(){
        System.out.println("Eager Bean Created !!");
    }

    public  void start(){
        System.out.println("Bean has been started");
    }
}
