package com.bootbank.loan.model.dto;

import com.bootbank.loan.model.enums.Currency;
import com.bootbank.loan.model.enums.Type;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record LoanPaymentScheduleResponseDto(Long id,
                                             String name,
                                             Type type,
                                             BigDecimal amount,
                                             BigDecimal rate,
                                             Integer term,
                                             BigDecimal monthlyPayment,
                                             Currency currency,
                                             List<LoanPaymentScheduleDto> schedule) {
}
