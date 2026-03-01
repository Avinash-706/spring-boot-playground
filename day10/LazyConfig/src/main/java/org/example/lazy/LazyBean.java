package org.example.lazy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class LazyBean {
    public LazyBean() {
        System.out.println("Lazy Bean Created !!");
    }

    public  void start(){
        System.out.println("Bean has been started : LAZY");
    }
}
