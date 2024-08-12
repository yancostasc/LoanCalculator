package com.loancalculator.loancalculator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.loancalculator.loancalculator.model.LoanDetails;
import com.loancalculator.loancalculator.model.LoanRequest;

@Service
public class LoanCalculatorService {

	private static final int BASE_DAYS = 360;
	private static final int TOTAL_INSTALLMENTS = 120;

	public List<LoanDetails> calculatePayments(LoanRequest request) {
		List<LoanDetails> paymentDetails = new ArrayList<>();

		// Validações preliminares
		if (request.getEndDate().isBefore(request.getStartDate())) {
			throw new IllegalArgumentException("A data final deve ser maior que a data inicial");
		}
		if (request.getFirstPaymentDate().isBefore(request.getStartDate())
				|| request.getFirstPaymentDate().isAfter(request.getEndDate())) {
			throw new IllegalArgumentException(
					"A data do primeiro pagamento deve estar entre a data inicial e a data final");
		}

		LocalDate currentDate = request.getStartDate();
		double remainingBalance = request.getLoanAmount();
		double monthlyAmortization = request.getLoanAmount() / TOTAL_INSTALLMENTS;
		double interestRate = request.getInterestRate() / 100.0;
		double accumulatedInterest = 0.0;
		double paid = 0;

		// Linha 1: 01/01/2024
		LoanDetails firstLine = createLoanDetail(currentDate, request.getLoanAmount(), remainingBalance, 0.0, 0.0,
				accumulatedInterest, 0, false, paid);
		paymentDetails.add(firstLine);

		// Linha 2: 31/01/2024
		LocalDate endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
		double provisionInterest = calculateProvisionInterest(remainingBalance, accumulatedInterest, interestRate,
				currentDate, endOfMonth);
		accumulatedInterest += provisionInterest;
		LoanDetails secondLine = createLoanDetail(endOfMonth, 0, remainingBalance, 0.0, provisionInterest,
				accumulatedInterest, 0, false, paid);
		paymentDetails.add(secondLine);

		// Atualizar currentDate para a data do primeiro pagamento (15/02/2024)
		currentDate = request.getFirstPaymentDate();

		// Loop para calcular cada parcela a partir do primeiro pagamento
		for (int i = 0; i < TOTAL_INSTALLMENTS; i++) {
		    // Cálculo dos juros provisionados desde a última data
		    provisionInterest = calculateProvisionInterest(remainingBalance, accumulatedInterest, interestRate,
		            paymentDetails.get(paymentDetails.size() - 1).getLoan().getCompetencyDate(), currentDate);
		    accumulatedInterest += provisionInterest;

		    // Reduz o saldo devedor pela amortização mensal
		    remainingBalance -= monthlyAmortization;

		    // Adiciona detalhes da parcela atual
		    double newPaid = accumulatedInterest;
		    System.out.println("teste, provision and accumulated " + newPaid + " - " + provisionInterest + " - " + accumulatedInterest);
		    LoanDetails loanDetails = createLoanDetail(currentDate, 0, remainingBalance, monthlyAmortization,
		            provisionInterest, accumulatedInterest, i + 1, true, newPaid);
		    
		    // Zera o acumulado de juros quando for um dia de pagamento
		    accumulatedInterest = 0.0;

		    paymentDetails.add(loanDetails);

		    // Avança para o próximo mês, ajustando a data para o final do mês
		    endOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
		    if (!endOfMonth.isEqual(currentDate)) {
		        provisionInterest = calculateProvisionInterest(remainingBalance, accumulatedInterest, interestRate,
		                currentDate, endOfMonth);
		        accumulatedInterest += provisionInterest;
		        LoanDetails endOfMonthLine = createLoanDetail(endOfMonth, 0, remainingBalance, 0.0, provisionInterest,
		                accumulatedInterest, i + 1, false, paid);
		        paymentDetails.add(endOfMonthLine);
		    }

		    // Avança para a próxima data de pagamento
		    currentDate = currentDate.plusMonths(1);
		}

		return paymentDetails;
	}

	private LoanDetails createLoanDetail(LocalDate date, double loanAmount, double remainingBalance,
	        double amortization, double provisionInterest, double accumulatedInterest, int installmentNumber,
	        boolean isPaymentDay, double newPaid) {

	    LoanDetails loanDetails = new LoanDetails();
	    loanDetails.setLoan(new LoanDetails.Loan());
	    loanDetails.getLoan().setCompetencyDate(date);
	    loanDetails.getLoan().setLoanAmount(loanAmount);
	    loanDetails.getLoan().setOutstandingBalance(roundToTwoDecimalPlaces(remainingBalance));

	    loanDetails.setInstallment(new LoanDetails.Installment());
	    loanDetails.getInstallment().setTotal(isPaymentDay ? roundToTwoDecimalPlaces(amortization + (provisionInterest + accumulatedInterest)) : 0.0);
	    loanDetails.getInstallment().setConsolidated(isPaymentDay ? installmentNumber + "/" + TOTAL_INSTALLMENTS : null);

	    loanDetails.setPrincipal(new LoanDetails.Principal());
	    loanDetails.getPrincipal().setAmortization(isPaymentDay ? roundToTwoDecimalPlaces(amortization) : 0.0);
	    loanDetails.getPrincipal().setBalance(roundToTwoDecimalPlaces(remainingBalance));

	    loanDetails.setInterest(new LoanDetails.Interest());
	    loanDetails.getInterest().setProvision(roundToTwoDecimalPlaces(provisionInterest));
	    loanDetails.getInterest().setPaid(isPaymentDay ? roundToTwoDecimalPlaces(newPaid) : 0.0);
	    loanDetails.getInterest().setAccumulated(!isPaymentDay ? roundToTwoDecimalPlaces(accumulatedInterest) : 0.0);

	    return loanDetails;
	}


	private double calculateProvisionInterest(double remainingBalance, double accumulatedInterest, double interestRate, LocalDate startDate, LocalDate endDate) {
	    long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
	    return roundToTwoDecimalPlaces(
	        ((Math.pow(1 + interestRate, (double) daysBetween / BASE_DAYS) - 1) * (remainingBalance + accumulatedInterest))
	    );
	}

	private double roundToTwoDecimalPlaces(double value) {
		return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}
