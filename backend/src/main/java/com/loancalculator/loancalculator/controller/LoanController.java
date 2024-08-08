package com.loancalculator.loancalculator.controller;

import com.loancalculator.loancalculator.model.LoanRequest;
import com.loancalculator.loancalculator.service.LoanCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanCalculatorService loanCalculatorService;

    @PostMapping("/calculate")
    public List<LoanCalculatorService.Payment> calculate(@RequestBody LoanRequest loanRequest) {
        return loanCalculatorService.calculatePayments(loanRequest);
    }
}
