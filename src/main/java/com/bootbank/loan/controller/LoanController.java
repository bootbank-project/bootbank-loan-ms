package com.bootbank.loan.controller;

import com.bootbank.loan.model.dto.LoanApplyRequestDto;
import com.bootbank.loan.model.dto.LoanDto;
import com.bootbank.loan.model.dto.LoanPayRequestDto;
import com.bootbank.loan.model.dto.LoanPaymentScheduleResponseDto;
import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<LoanResponseDto> getCustomerLoans(@RequestHeader("X-CIF") String cif) {
        LoanResponseDto response = loanService.getCustomerLoans(cif);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/apply")
    public ResponseEntity<LoanDto> applyLoan(
            @RequestHeader("X-CIF") String cif,
            @RequestBody LoanApplyRequestDto request) {
        LoanDto response = loanService.applyLoan(cif, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/schedule")
    public ResponseEntity<LoanPaymentScheduleResponseDto> getPaymentSchedule(
            @RequestHeader("X-CIF") String cif,
            @PathVariable("id") Long id) {
        LoanPaymentScheduleResponseDto response = loanService.getPaymentSchedule(cif, id);
        return ResponseEntity.ok(response);
    }

    // Добавленный endpoint по ТЗ 5.6
    @PostMapping("/{id}/pay")
    public ResponseEntity<LoanDto> payLoan(
            @PathVariable("id") Long id,
            @RequestHeader("X-CIF") String cif,
            @Valid @RequestBody LoanPayRequestDto payRequest) {
        LoanDto response = loanService.payLoan(id, cif, payRequest);
        return ResponseEntity.ok(response);
    }
}