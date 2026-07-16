package com.bootbank.loan.service;

import com.bootbank.loan.model.dto.LoanResponseDto;

public interface LoanService {

    LoanResponseDto getCustomerLoans(String cif);

}
