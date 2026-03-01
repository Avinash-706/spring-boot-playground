package org.example.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class IncomeValidator implements LoanValidator {

    @Override
    public void validateLoan(double amount) {
        System.out.println("IncomeValidator: Checking income for loan of $" + amount);
    }
}
