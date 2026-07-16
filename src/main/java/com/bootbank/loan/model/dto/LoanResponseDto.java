package com.bootbank.loan.model.dto;

import com.bootbank.loan.model.enums.Currency;
import com.bootbank.loan.model.enums.Status;
import com.bootbank.loan.model.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record LoanResponseDto(Long id,
                              String name,
                              Type type,
                              BigDecimal amount,
                              BigDecimal rate,
                              BigDecimal monthlyPayment,
                              Currency currency,
                              LocalDate startDate,
                              LocalDate endDate,
                              Status status,
                              BigDecimal remaining,
                              BigDecimal totalPaid,
                              Integer paymentsLeft,
                              LocalDate nextPaymentDate) {
}
