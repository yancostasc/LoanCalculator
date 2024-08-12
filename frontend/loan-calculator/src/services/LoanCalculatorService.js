import axios from "axios";

const API_URL = "http://localhost:8080/api/loans/calculate";

export const calculateLoan = (loanData) => {
  return axios.post(API_URL, loanData);
};
