package com.bootbank.template.mapper;

import com.bootbank.template.model.dto.LoanRequestDto;
import com.bootbank.template.model.dto.LoanResponseDto;
import com.bootbank.template.model.entity.LoanEntity;

public class LoanMapper {

    public static LoanResponseDto mapEntityToResponse(LoanEntity loan) {
        return new LoanResponseDto(
                loan.getCif(),
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

    public static LoanEntity mapRequestToEntity(LoanRequestDto request) {
        return LoanEntity.builder()
                .cif(request.cif())
                .name(request.name())
                .type(request.type())
                .amount(request.amount())
                .rate(request.rate())
                .monthlyPayment(request.monthlyPayment())
                .currency(request.currency())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .status(request.status())
                .remaining(request.remaining())
                .totalPaid(request.totalPaid())
                .paymentsLeft(request.paymentsLeft())
                .nextPaymentDate(request.nextPaymentDate())
                .build();
    }
}
