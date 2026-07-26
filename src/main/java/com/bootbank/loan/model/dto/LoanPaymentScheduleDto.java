package com.bootbank.loan.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record LoanPaymentScheduleDto(Integer month,
                                     LocalDate paymentDate,
                                     BigDecimal payment,
                                     BigDecimal principal,
                                     BigDecimal interest,
                                     BigDecimal balance,
                                     String status) {
}
