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
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        LocalDate firstPaymentDate = request.getFirstPaymentDate();
        double loanAmount = request.getLoanAmount();
        double interestRate = request.getInterestRate() / 100;
        int totalInstallments = 120;
        double baseDays = 360;

        LocalDate currentDate = startDate;
        double remainingBalance = loanAmount;

        while (currentDate.isBefore(endDate.plusDays(1))) {
            Payment payment = new Payment();
            payment.setDate(currentDate);
            payment.setLoanAmount(loanAmount);
            payment.setRemainingBalance(remainingBalance);

            double installment = loanAmount / totalInstallments;
            payment.setInstallment(installment);

            double interest = (remainingBalance * Math.pow((1 + interestRate), (ChronoUnit.DAYS.between(currentDate.minusDays(1), currentDate) / baseDays))) - remainingBalance;
            payment.setInterest(interest);

            remainingBalance -= installment;
            payment.setRemainingBalance(remainingBalance);

            payments.add(payment);

            currentDate = currentDate.plusMonths(1);
        }

        return payments;
    }

    public class Payment {
        private LocalDate date;
        private double loanAmount;
        private double remainingBalance;
        private double installment;
        private double interest;
        
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
    }
}
