package org.example.bean_scope;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = "org.example.bean_scope")
public class BeanScopeConfig {

}
