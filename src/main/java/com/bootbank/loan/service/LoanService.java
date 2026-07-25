package com.bootbank.loan.service;

import com.bootbank.loan.model.dto.LoanApplyRequestDto;
import com.bootbank.loan.model.dto.LoanDto;
import com.bootbank.loan.model.dto.LoanResponseDto;

public interface LoanService {

    LoanResponseDto getCustomerLoans(String cif);

    LoanDto applyLoan(String cif, LoanApplyRequestDto request);

}