package com.bootbank.template.service;

import com.bootbank.template.exceptions.exception.RecordNotFoundException;
import com.bootbank.template.mapper.LoanMapper;
import com.bootbank.template.model.dto.LoanResponseDto;
import com.bootbank.template.model.entity.LoanEntity;
import com.bootbank.template.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<LoanResponseDto> getCustomerLoans(String cif) {
        List<LoanEntity> loans = loanRepository.findByCif(cif);

        if(loans.isEmpty()) {
            throw new RecordNotFoundException("Loan not found for Customer ID: "+cif);
        }

        return loans.stream().map(LoanMapper::mapEntityToResponse).toList();
    }

}
