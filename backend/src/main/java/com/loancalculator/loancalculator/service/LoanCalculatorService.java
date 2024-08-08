package com.loancalculator.loancalculator.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.loancalculator.loancalculator.model.LoanRequest;

@Service
public class LoanCalculatorService {

    public List<Payment> calculatePayments(LoanRequest request) {
        List<Payment> payments = new ArrayList<>();
        LocalDate currentDate = request.getStartDate();
        LocalDate firstPaymentDate = request.getFirstPaymentDate();
        double loanAmount = request.getLoanAmount();
        double interestRate = request.getInterestRate() / 100;
        double baseDays = request.getBaseDays();
        int totalInstallments = request.getTotalInstallments();

        double remainingBalance = loanAmount;
        double accumulated = 0;
        double paid = 0;

        LocalDate previousDate = currentDate.minusDays(15);

        while (!currentDate.isAfter(request.getEndDate())) {
            Payment payment = new Payment();

            double daysBetween = ChronoUnit.DAYS.between(previousDate, currentDate);
            double provision = Math.pow(1 + (interestRate / baseDays), daysBetween) - 1 * (remainingBalance + accumulated);

            double amortization = (totalInstallments > 0) ? loanAmount / totalInstallments : 0;
            double accumulatedNew = accumulated + provision - paid;
            double paidNew = (currentDate.isAfter(firstPaymentDate)) ? accumulated + provision : 0;

            payment.setDate(currentDate);
            payment.setLoanAmount(loanAmount);
            payment.setRemainingBalance(remainingBalance);

            if (isEndOfMonth(currentDate)) {
                payment.setInstallment(0);
                payment.setAmortization(0);
                payment.setInterest(0);
            } else {
                payment.setInstallment(amortization);
                payment.setAmortization(amortization);
                payment.setInterest(provision);
            }

            payment.setProvision(provision);
            payment.setAccumulated(accumulatedNew);
            payment.setPaid(paidNew);

            payments.add(payment);

            remainingBalance -= amortization;
            accumulated = accumulatedNew;
            paid = paidNew;

            previousDate = currentDate;
            currentDate = getNextPaymentDate(currentDate);
        }

        return payments;
    }

    private boolean isEndOfMonth(LocalDate date) {
        return date.getDayOfMonth() == date.withDayOfMonth(date.lengthOfMonth()).getDayOfMonth();
    }

    private LocalDate getNextPaymentDate(LocalDate date) {
        if (date.getDayOfMonth() <= 15) {
            return date.withDayOfMonth(15).plusMonths(1);
        } else {
            return date.withDayOfMonth(date.lengthOfMonth()).plusMonths(1);
        }
    }

    public class Payment {
        private LocalDate date;
        private double loanAmount;
        private double remainingBalance;
        private double installment;
        private double interest;
        private double amortization;
        private double provision;
        private double accumulated;
        private double paid;

        public LocalDate getDate() {
            return date;
        }
        public void setDate(LocalDate date) {
            this.date = date;
        }
        public double getLoanAmount() {
            return loanAmount;
        }
        public void setLoanAmount(double loanAmount) {
            this.loanAmount = loanAmount;
        }
        public double getRemainingBalance() {
            return remainingBalance;
        }
        public void setRemainingBalance(double remainingBalance) {
            this.remainingBalance = remainingBalance;
        }
        public double getInstallment() {
            return installment;
        }
        public void setInstallment(double installment) {
            this.installment = installment;
        }
        public double getInterest() {
            return interest;
        }
        public void setInterest(double interest) {
            this.interest = interest;
        }
        public double getAmortization() {
            return amortization;
        }
        public void setAmortization(double amortization) {
            this.amortization = amortization;
        }
        public double getProvision() {
            return provision;
        }
        public void setProvision(double provision) {
            this.provision = provision;
        }
        public double getAccumulated() {
            return accumulated;
        }
        public void setAccumulated(double accumulated) {
            this.accumulated = accumulated;
        }
        public double getPaid() {
            return paid;
        }
        public void setPaid(double paid) {
            this.paid = paid;
        }
    }
}
