package com.loancalculator.loancalculator.model;

import java.time.LocalDate;

public class LoanDetails {

    private Loan loan;
    private Installment installment;
    private Principal principal;
    private Interest interest;

    public Loan getLoan() {
        return loan;
    }

    public void setLoan(Loan loan) {
        this.loan = loan;
    }

    public Installment getInstallment() {
        return installment;
    }

    public void setInstallment(Installment installment) {
        this.installment = installment;
    }

    public Principal getPrincipal() {
        return principal;
    }

    public void setPrincipal(Principal principal) {
        this.principal = principal;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public static class Loan {
        private LocalDate competencyDate;
        private double loanAmount;
        private double outstandingBalance;

        public LocalDate getCompetencyDate() {
            return competencyDate;
        }

        public void setCompetencyDate(LocalDate competencyDate) {
            this.competencyDate = competencyDate;
        }

        public double getLoanAmount() {
            return loanAmount;
        }

        public void setLoanAmount(double loanAmount) {
            this.loanAmount = loanAmount;
        }

        public double getOutstandingBalance() {
            return outstandingBalance;
        }

        public void setOutstandingBalance(double outstandingBalance) {
            this.outstandingBalance = outstandingBalance;
        }

    }

    public static class Installment {
        private String consolidated;
        private double total;

        public String getConsolidated() {
            return consolidated;
        }

        public void setConsolidated(String consolidated) {
            this.consolidated = consolidated;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

    }

    public static class Principal {
        private double amortization;
        private double balance;

        public double getAmortization() {
            return amortization;
        }

        public void setAmortization(double amortization) {
            this.amortization = amortization;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

    }

    public static class Interest {
        private double provision;
        private double accumulated;
        private double paid;

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
