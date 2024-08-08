package com.loancalculator.loancalculator.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.loancalculator.loancalculator.model.LoanDetails;
import com.loancalculator.loancalculator.model.LoanDetails.Installment;
import com.loancalculator.loancalculator.model.LoanDetails.Interest;
import com.loancalculator.loancalculator.model.LoanDetails.Loan;
import com.loancalculator.loancalculator.model.LoanDetails.Principal;
import com.loancalculator.loancalculator.model.LoanRequest;

@Service
public class LoanCalculatorService {

    public List<LoanDetails> calculatePayments(LoanRequest request) {
        List<LoanDetails> loanDetailsList = new ArrayList<>();
        LocalDate currentDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        double balance = request.getLoanAmount();
        double accumulated = 0;

        // Add details for startDate and end of the month of startDate
        addLoanDetailsForDate(loanDetailsList, request, currentDate, balance, accumulated, 0);
        LocalDate endOfMonthDate = getEndOfMonth(currentDate);
        if (!endOfMonthDate.isAfter(endDate)) {
            addLoanDetailsForDate(loanDetailsList, request, endOfMonthDate, balance, accumulated, 0);
        }

        // Calculate payments from firstPaymentDate onwards
        LocalDate firstPaymentDate = request.getFirstPaymentDate();
        currentDate = firstPaymentDate;
        
        for (int i = 1; i < request.getTotalInstallments(); i++) {
            addLoanDetailsForDate(loanDetailsList, request, currentDate, balance, accumulated, i);
            LocalDate endOfMonth = getEndOfMonth(currentDate);
            if (currentDate.isBefore(endDate)) {
                addLoanDetailsForDate(loanDetailsList, request, endOfMonth, balance, accumulated, i);
            }
            currentDate = currentDate.plusMonths(1);
        }

        return loanDetailsList;
    }

    private void addLoanDetailsForDate(List<LoanDetails> loanDetailsList, LoanRequest request, LocalDate date, double balance, double accumulated, int installmentNumber) {
        boolean isPayday = isPayday(request.getFirstPaymentDate(), date, request.getEndDate());

        LoanDetails details = new LoanDetails();
        double amortization = isPayday ? request.getLoanAmount() / request.getTotalInstallments() : 0;
        double provision = calculateProvision(request, balance, accumulated);
        double total = amortization + (isPayday ? accumulated + provision : 0);
        double paid = isPayday ? accumulated + provision : 0;
        
        balance -= amortization;
        accumulated += provision - paid;

        details.setLoan(createLoan(request, date, balance, accumulated));
        details.setInstallment(createInstallment(isPayday, installmentNumber + 1, request.getTotalInstallments(), total));
        details.setPrincipal(createPrincipal(amortization, balance));
        details.setInterest(createInterest(provision, accumulated, paid));

        loanDetailsList.add(details);
    }

    private boolean isPayday(LocalDate firstPaymentDate, LocalDate paymentDate, LocalDate endDate) {
        return firstPaymentDate.isEqual(paymentDate) || (firstPaymentDate.isBefore(paymentDate) && paymentDate.isBefore(endDate.plusMonths(1)));
    }

    private double calculateProvision(LoanRequest request, double balance, double accumulated) {
        double daysBetween = ChronoUnit.DAYS.between(request.getStartDate(), request.getStartDate().plusMonths(1));
        double provision = Math.pow(1 + request.getInterestRate(), daysBetween / request.getBaseDays()) - 1;
        return provision * (balance + accumulated);
    }

    private Loan createLoan(LoanRequest request, LocalDate currentDate, double balance, double accumulated) {
        Loan loan = new Loan();
        loan.setCompetencyDate(currentDate);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setOutstandingBalance(balance + accumulated);
        return loan;
    }

    private Installment createInstallment(boolean isPayday, int installmentNumber, int totalInstallments, double total) {
        Installment installment = new Installment();
        installment.setConsolidated(isPayday ? installmentNumber + "/" + totalInstallments : null);
        installment.setTotal(total);
        return installment;
    }

    private Principal createPrincipal(double amortization, double balance) {
        Principal principal = new Principal();
        principal.setAmortization(amortization);
        principal.setBalance(balance);
        return principal;
    }

    private Interest createInterest(double provision, double accumulated, double paid) {
        Interest interest = new Interest();
        interest.setProvision(provision);
        interest.setAccumulated(accumulated);
        interest.setPaid(paid);
        return interest;
    }

    private LocalDate getEndOfMonth(LocalDate date) {
        return date.withDayOfMonth(date.getMonth().length(date.isLeapYear()));
    }
}
