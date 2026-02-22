package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // Marks this as Spring configuration class
public class AppConfig {
    @Bean  // Tells Spring to manage this object as a Bean
    public MessageService messageService() {
        return new MessageService();
    }
}
