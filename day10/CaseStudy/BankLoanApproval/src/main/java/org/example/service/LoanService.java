package org.example.service;

import org.example.entity.LoanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class LoanService {
    private final LoanValidator loanValidator;
    private AuditService auditService;

    @Autowired
    public LoanService(@Qualifier("incomeValidator") LoanValidator loanValidator){
        this.loanValidator = loanValidator;
        System.out.println("LoanService created with " + loanValidator.getClass().getSimpleName());
    }

    @Autowired
    public void setAuditService(AuditService auditService){
        this.auditService = auditService;
    }

    public void processLoan(double amount) {
        System.out.println("LoanService: Processing loan application");
        auditService.logLoanRequest(amount, loanValidator.getClass().getSimpleName());
        loanValidator.validateLoan(amount);
        System.out.println("LoanService: Loan approved");
    }
}
