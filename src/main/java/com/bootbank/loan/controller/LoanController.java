package com.bootbank.loan.controller;

import com.bootbank.loan.model.dto.LoanApplyRequestDto;
import com.bootbank.loan.model.dto.LoanDto;
import com.bootbank.loan.model.dto.LoanPaymentScheduleResponseDto;
import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/customer")
    public ResponseEntity<LoanResponseDto> getCustomerLoans(@RequestHeader("cif") String cif) {
        return ResponseEntity.ok(loanService.getCustomerLoans(cif));
    }

    @Operation(summary = "Submit a new loan application and automatically create an active loan with its payment schedule")
    @PostMapping(value = "/apply", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoanDto> applyLoan(@RequestHeader("X-CIF") String cif,
                                             @Valid @RequestBody LoanApplyRequestDto request) {
        LoanDto createdLoan = loanService.applyLoan(cif, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoan);
    }

    @Operation(summary = "Get the full payment schedule for a loan belonging to the authenticated customer")
    @GetMapping("/{id}/payment-schedule")
    public ResponseEntity<LoanPaymentScheduleResponseDto> getPaymentSchedule(@RequestHeader("X-CIF") String cif,
                                                                             @PathVariable Long id) {
        return ResponseEntity.ok(loanService.getPaymentSchedule(cif, id));
    }
@Operation(summary = "Make payment for a customer loan")
@PostMapping("/{id}/pay")
public ResponseEntity<LoanDto> payLoan(
        @RequestHeader("X-CIF") String cif,
        @PathVariable Long id,
        @Valid @RequestBody PaymentRequestDto request) {

    LoanDto loan = loanService.payLoan(cif, id, request);
    return ResponseEntity.ok(loan);
}
}
