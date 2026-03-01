package org.example;

import org.example.config.BankAppConfig;
import org.example.entity.LoanValidator;
import org.example.service.LoanService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        AnnotationConfigApplicationContext ref =
                new AnnotationConfigApplicationContext(BankAppConfig.class);

        System.out.println("\n--- Using IncomeValidator (via @Qualifier) ---");
        LoanService loanService = ref.getBean(LoanService.class);
        loanService.processLoan(50000);

        System.out.println("\n--- Using CreditScoreValidator (via @Primary) ---");
        LoanValidator creditValidator = ref.getBean( LoanValidator.class);
        creditValidator.validateLoan(75000);

        System.out.println();
        ref.close();
        System.out.println("Application closed");
    }
}
