package com.bootbank.loan.service.impl;

import com.bootbank.loan.exceptions.exception.RecordNotFoundException;
import com.bootbank.loan.mapper.LoanMapper;
import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.model.entity.LoanEntity;
import com.bootbank.loan.repository.LoanRepository;
import com.bootbank.loan.service.LoanService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public LoanResponseDto getCustomerLoans(String cif) {
        List<LoanEntity> loans = loanRepository.findByCif(cif);

        if (loans.isEmpty()) {
            throw new RecordNotFoundException("Loan not found for Customer ID: " + cif);
        }

        var loanDtos = loans.stream().map(LoanMapper::mapEntityToResponse).toList();
        return LoanResponseDto.builder()
                .loans(loanDtos)
                .build();
    }

}
