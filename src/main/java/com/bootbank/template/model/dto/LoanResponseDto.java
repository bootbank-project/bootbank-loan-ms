package com.bootbank.template.model.dto;

import com.bootbank.template.model.enums.Currency;
import com.bootbank.template.model.enums.Status;
import com.bootbank.template.model.enums.Type;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanResponseDto(Long id,
                              String name,
                              Type type,
                              BigDecimal amount,
                              Double rate,
                              BigDecimal monthlyPayment,
                              Currency currency,
                              LocalDate startDate,
                              LocalDate endDate,
                              Status status,
                              BigDecimal remaining,
                              BigDecimal totalPaid,
                              Integer paymentsLeft,
                              LocalDate nextPaymentDate)
{
}
