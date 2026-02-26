package org.example.primary_qualifier;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// @Configuration tells Spring this class contains configuration
@Configuration
// @ComponentScan tells Spring where to look for @Component classes
@ComponentScan(basePackages = "org.example.primary_qualifier")
public class PrimaryQualifierConfig {
    // This class is empty because we're using component scanning
    // Spring will automatically find all @Component classes in the package
}
