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

  const handleSubmit = async (e) => {
    e.preventDefault();

    const loanData = {
      startDate: formatDate(startDate),
      endDate: formatDate(endDate),
      firstPaymentDate: formatDate(firstPaymentDate),
      loanAmount: parseFloat(loanAmount),
      interestRate: parseFloat(interestRate),
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
        />
      </label>
      <label>
        Data Final:
        <input
          type="date"
          value={endDate}
          onChange={(e) => setEndDate(e.target.value)}
          required
        />
      </label>
      <label>
        Primeiro Pagamento:
        <input
          type="date"
          value={firstPaymentDate}
          onChange={(e) => setFirstPaymentDate(e.target.value)}
          required
        />
      </label>
      <label>
        Valor do Empréstimo:
        <input
          type="number"
          value={loanAmount}
          onChange={(e) => setLoanAmount(e.target.value)}
          required
        />
      </label>
      <label>
        Taxa de Juros:
        <input
          type="number"
          step="0.01"
          value={interestRate}
          onChange={(e) => setInterestRate(e.target.value)}
          required
        />
      </label>
      <button type="submit">Calcular</button>
    </form>
  );
};

export default LoanCalculatorForm;
