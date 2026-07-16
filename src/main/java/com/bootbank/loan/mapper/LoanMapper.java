package com.bootbank.loan.mapper;

import com.bootbank.loan.model.dto.LoanDto;
import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.model.entity.LoanEntity;

public class LoanMapper {

    public static LoanDto mapEntityToResponse(LoanEntity loan) {

        return LoanDto.builder()
                .id(loan.getId())
                .name(loan.getName())
                .type(loan.getType())
                .amount(loan.getAmount())
                .rate(loan.getRate())
                .monthlyPayment(loan.getMonthlyPayment())
                .currency(loan.getCurrency())
                .startDate(loan.getStartDate())
                .endDate(loan.getEndDate())
                .status(loan.getStatus())
                .remaining(loan.getRemaining())
                .totalPaid(loan.getTotalPaid())
                .paymentsLeft(loan.getPaymentsLeft())
                .nextPaymentDate(loan.getNextPaymentDate())
                .build();
    }
}
