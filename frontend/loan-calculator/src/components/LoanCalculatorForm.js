import React, { useState } from "react";
import { calculateLoan } from "../services/LoanCalculatorService";

const LoanCalculatorForm = ({ setResults }) => {
  const [startDate, setStartDate] = useState("");
  const [endDate, setEndDate] = useState("");
  const [firstPaymentDate, setFirstPaymentDate] = useState("");
  const [loanAmount, setLoanAmount] = useState("");
  const [interestRate, setInterestRate] = useState("");

  const formatDate = (date) => {
    const [year, month, day] = date.split("-");
    return `${year}-${month}-${day}`;
  };

  const formatNumber = (number) => {
    return new Intl.NumberFormat("pt-BR", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(number);
  };

  const handleLoanAmountChange = (e) => {
    const value = e.target.value.replace(/\D/g, "");
    setLoanAmount(formatNumber(value / 100));
  };

  const handleInterestRateChange = (e) => {
    const value = e.target.value.replace(/\D/g, "");
    setInterestRate(formatNumber(value / 100));
  };

  const isFormValid = () => {
    return (
      startDate !== "" &&
      endDate !== "" &&
      firstPaymentDate !== "" &&
      loanAmount !== "" &&
      interestRate !== ""
    );
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const loanData = {
      startDate: formatDate(startDate),
      endDate: formatDate(endDate),
      firstPaymentDate: formatDate(firstPaymentDate),
      loanAmount: parseFloat(loanAmount.replace(/\./g, "").replace(",", ".")),
      interestRate: parseFloat(
        interestRate.replace(/\./g, "").replace(",", ".")
      ),
    };

    try {
      const response = await calculateLoan(loanData);
      setResults(response.data);
    } catch (error) {
      console.error("Erro ao calcular o empréstimo", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Data Inicial:
        <input
          type="date"
          value={startDate}
          onChange={(e) => setStartDate(e.target.value)}
          required
          placeholder="dd/mm/aaaa"
        />
      </label>
      <label>
        Data Final:
        <input
          type="date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
          required
          placeholder="dd/mm/aaaa"
        />
      </label>
      <label>
        Primeiro Pagamento:
        <input
          type="date"
          value={firstPaymentDate}
          onChange={(e) => setFirstPaymentDate(e.target.value)}
          required
          placeholder="dd/mm/aaaa"
        />
      </label>
      <label>
        Valor do Empréstimo:
        <input
          type="text"
          value={loanAmount}
          onChange={handleLoanAmountChange}
          required
          placeholder="Ex: 140.000,00"
        />
      </label>
      <label>
        Taxa de Juros:
        <div className="input-with-percent">
          <input
            type="text"
            value={interestRate}
            onChange={handleInterestRateChange}
            required
            placeholder="Ex: 7,00"
          />
          <span className="percent-symbol">%</span>
        </div>
      </label>
      <button type="submit" disabled={!isFormValid()}>
        Calcular
      </button>
    </form>
  );
};

export default LoanCalculatorForm;
