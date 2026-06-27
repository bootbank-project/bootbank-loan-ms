package com.bootbank.template.controller;


import com.bootbank.template.model.dto.LoanResponseDto;
import com.bootbank.template.service.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/customer/{cif}")
    public List<LoanResponseDto> getCustomerLoans(@PathVariable String cif) {
        return loanService.getCustomerLoans(cif);
    }

}

