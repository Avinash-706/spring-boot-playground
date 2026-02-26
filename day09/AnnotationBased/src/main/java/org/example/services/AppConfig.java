package org.example.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// @Configuration tells Spring this class contains configuration
@Configuration
// @ComponentScan tells Spring where to look for @Component classes
@ComponentScan(basePackages = "org.example.services")
public class AppConfig {
    // This class is empty because we're using component scanning
    // Spring will automatically find all @Component classes in the package
}
