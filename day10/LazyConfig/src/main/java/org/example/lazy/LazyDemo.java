package org.example.lazy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LazyDemo {
    public static void main(String[] args) {
        System.out.println("== Container Created ==");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LazyConfig.class);


        context.close();
    }
}