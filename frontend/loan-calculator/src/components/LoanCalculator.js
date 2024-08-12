import React, { useState } from "react";
import LoanCalculatorForm from "./LoanCalculatorForm";
import LoanCalculatorResult from "./LoanCalculatorResult";

const LoanCalculator = () => {
  const [results, setResults] = useState(null);

  return (
    <div className="container">
      <h1>Calculadora de Empréstimos</h1>
      <LoanCalculatorForm setResults={setResults} />
      <LoanCalculatorResult results={results} />
    </div>
  );
};

export default LoanCalculator;
