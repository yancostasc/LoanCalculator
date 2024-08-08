package com.loancalculator.loancalculator.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LoanRequest {
    @NotNull(message = "Data inicial é obrigatória")
    private LocalDate startDate;

    @NotNull(message = "Data final é obrigatória")
    private LocalDate endDate;

    @NotNull(message = "Primeiro pagamento é obrigatório")
    private LocalDate firstPaymentDate;

    @Positive(message = "Valor do empréstimo deve ser positivo")
    private double loanAmount;

    @Positive(message = "Taxa de juros deve ser positiva")
    private double interestRate;

    @Positive(message = "Base de dias deve ser positiva")
    private double baseDays;

    @Positive(message = "Quantidade de parcelas deve ser positiva")
    private int totalInstallments;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getFirstPaymentDate() {
        return firstPaymentDate;
    }

    public void setFirstPaymentDate(LocalDate firstPaymentDate) {
        this.firstPaymentDate = firstPaymentDate;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getBaseDays() {
        return baseDays;
    }

    public void setBaseDays(double baseDays) {
        this.baseDays = baseDays;
    }

    public int getTotalInstallments() {
        return totalInstallments;
    }

    public void setTotalInstallments(int totalInstallments) {
        this.totalInstallments = totalInstallments;
    }
}
