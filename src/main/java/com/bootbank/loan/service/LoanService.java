package com.bootbank.loan.service;

import com.bootbank.loan.exceptions.exception.RecordNotFoundException;
import com.bootbank.loan.mapper.LoanMapper;
import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.model.entity.LoanEntity;
import com.bootbank.loan.repository.LoanRepository;
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

        if (loans.isEmpty()) {
            throw new RecordNotFoundException("Loan not found for Customer ID: " + cif);
        }

        return loans.stream().map(LoanMapper::mapEntityToResponse).toList();
    }

}
