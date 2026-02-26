package org.example.bean_scope;

import org.springframework.stereotype.Component;

@Component
public class SingletonBean {
    public SingletonBean(){
        System.out.println("SingletonBean created");
    }
}
