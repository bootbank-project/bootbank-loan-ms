package com.bootbank.loan.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record LoanApplyRequestDto(

        @NotBlank(message = "Kredit tipi (loanType) daxil edilməlidir.")
        String loanType,

        @NotNull(message = "Məbləğ (amount) daxil edilməlidir.")
        @DecimalMin(value = "0.0", inclusive = false, message = "Kredit məbləği 0-dan böyük olmalıdır.")
        BigDecimal amount,

        @NotNull(message = "Müddət (term) daxil edilməlidir.")
        @Min(value = 1, message = "Kreditin müddəti ən azı 1 ay olmalıdır.")
        Integer term,

        String purpose
) {
}