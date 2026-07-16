package com.bootbank.loan.controller;


import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @GetMapping("/customer")
    public ResponseEntity<LoanResponseDto> getCustomerLoans(@RequestHeader("cif") String cif) {
        return ResponseEntity.ok(loanService.getCustomerLoans(cif)) ;
    }

}

