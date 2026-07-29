package com.bootbank.loan.model.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record LoanResponseDto(List<LoanDto> loans) {
}
