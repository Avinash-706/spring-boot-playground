package org.example.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("--- Container Starting ---\n");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(lifeCycleConfig.class);

        System.out.println("\n-- Using Bean --");
        dbConnection dbConnection = context.getBean(dbConnection.class);
        dbConnection.executeQuery();

        System.out.println( "\n--- Container Closing ---" );
        context.close();
    }
}
