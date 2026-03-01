package org.example.entity;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CreditScoreValidator implements LoanValidator {

    @Override
    public void validateLoan(double amount) {
        System.out.println("CreditScoreValidator: Checking credit score for loan of $" + amount);
    }
}
