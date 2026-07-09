package com.bootbank.loan.mapper;

import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.model.entity.LoanEntity;

public class LoanMapper {

    public static LoanResponseDto mapEntityToResponse(LoanEntity loan) {
        return new LoanResponseDto(
                loan.getId(),
                loan.getName(),
                loan.getType(),
                loan.getAmount(),
                loan.getRate(),
                loan.getMonthlyPayment(),
                loan.getCurrency(),
                loan.getStartDate(),
                loan.getEndDate(),
                loan.getStatus(),
                loan.getRemaining(),
                loan.getTotalPaid(),
                loan.getPaymentsLeft(),
                loan.getNextPaymentDate()
        );
    }
}
