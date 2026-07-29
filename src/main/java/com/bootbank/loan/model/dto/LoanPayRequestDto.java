package com.bootbank.loan.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanPayRequestDto {

    @NotNull(message = "Ödəniş məbləği daxil edilməlidir.")
    @DecimalMin(value = "0.01", message = "Ödəniş məbləği 0-dan böyük olmalıdır.")
    private BigDecimal amount;
}