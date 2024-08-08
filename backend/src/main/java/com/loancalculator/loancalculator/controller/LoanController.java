package com.loancalculator.loancalculator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loancalculator.loancalculator.model.LoanDetails;
import com.loancalculator.loancalculator.model.LoanRequest;
import com.loancalculator.loancalculator.service.LoanCalculatorService;


@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanCalculatorService loanCalculatorService;

    @PostMapping("/calculate")
    public List<LoanDetails> calculate(@RequestBody LoanRequest loanRequest) {
        return loanCalculatorService.calculatePayments(loanRequest);
    }
}
