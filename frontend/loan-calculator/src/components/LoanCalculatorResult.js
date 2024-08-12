import React from "react";

const LoanCalculatorResult = ({ results }) => {
  if (!results || results.length === 0) {
    return <div>No results to display.</div>;
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
            <td>{item.loan.loanAmount}</td>
            <td>{item.loan.outstandingBalance}</td>
            <td>{item.installment.consolidated || "-"}</td>
            <td>{item.installment.total || "-"}</td>
            <td>{item.principal.amortization}</td>
            <td>{item.principal.balance}</td>
            <td>{item.interest.provision}</td>
            <td>{item.interest.accumulated}</td>
            <td>{item.interest.paid}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default LoanCalculatorResult;
