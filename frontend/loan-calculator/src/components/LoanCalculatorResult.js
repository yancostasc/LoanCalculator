import React from "react";

const LoanCalculatorResult = ({ results }) => {
  const formatNumber = (number) => {
    return new Intl.NumberFormat("pt-BR", {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    }).format(number);
  };

  if (!results || results.length === 0) {
    return null;
  }

  return (
    <table>
      <thead>
        <tr>
          <th rowSpan="2">Data Competência</th>
          <th colSpan="3" className="group-header">
            Empréstimo
          </th>
          <th colSpan="2" className="group-header">
            Parcela
          </th>
          <th colSpan="2" className="group-header">
            Principal
          </th>
          <th colSpan="2" className="group-header">
            Juros
          </th>
        </tr>
        <tr className="sub-header">
          <th>Valor de Empréstimo</th>
          <th>Saldo Devedor</th>
          <th>Consolidada</th>
          <th>Total</th>
          <th>Amortização</th>
          <th>Saldo</th>
          <th>Provisão</th>
          <th>Acumulado</th>
          <th>Pago</th>
        </tr>
      </thead>
      <tbody>
        {results.map((item, index) => (
          <tr key={index}>
            <td>{item.loan.competencyDate}</td>
            <td>{formatNumber(item.loan.loanAmount)}</td>
            <td>{formatNumber(item.loan.outstandingBalance)}</td>
            <td>
              {item.installment.consolidated
                ? item.installment.consolidated
                : ""}
            </td>
            <td>
              {item.installment.total
                ? formatNumber(item.installment.total)
                : ""}
            </td>
            <td>{formatNumber(item.principal.amortization)}</td>
            <td>{formatNumber(item.principal.balance)}</td>
            <td>{formatNumber(item.interest.provision)}</td>
            <td>{formatNumber(item.interest.accumulated)}</td>
            <td>{formatNumber(item.interest.paid)}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default LoanCalculatorResult;
