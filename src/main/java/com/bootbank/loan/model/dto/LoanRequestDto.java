package com.bootbank.loan.model.dto;

import com.bootbank.loan.model.enums.Currency;
import com.bootbank.loan.model.enums.Status;
import com.bootbank.loan.model.enums.Type;
import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanRequestDto(String cif,
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
                              LocalDate nextPaymentDate) {
}
