package com.loancalculator.loancalculator.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LoanRequest {
    @NotNull(message = "Data inicial é obrigatória")
     LocalDate startDate;

    @NotNull(message = "Data final é obrigatória")
    private LocalDate endDate;
    
    @NotNull(message = "Primeira data de pagamento é obrigatória")
    private LocalDate firstPaymentDate;

    @Positive(message = "Valor do empréstimo deve ser positivo")
    private double loanAmount;

    @Positive(message = "Taxa de juros deve ser positiva")
    private double interestRate;

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
    
}
