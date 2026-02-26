package org.example.bean_scope;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanScopeDemo {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanScopeConfig.class);

        System.out.println("=== Singleton Scope ====");
        SingletonBean s1 = context.getBean(SingletonBean.class);
        SingletonBean s2 = context.getBean(SingletonBean.class);
        System.out.println("Same Instance  ? " + (s1 == s2));

        System.out.println("\n=== Prototype Scope ===");
        PrototypeBean p1 = context.getBean(PrototypeBean.class);
        PrototypeBean p2 = context.getBean(PrototypeBean.class);
        System.out.println("Same Instance  ? " + (p1 == p2));

        ((AnnotationConfigApplicationContext)context).close();
    }
}
