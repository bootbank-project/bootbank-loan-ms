package com.bootbank.loan.repository;

import com.bootbank.loan.model.entity.LoanPaymentScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanPaymentScheduleRepository extends JpaRepository<LoanPaymentScheduleEntity, Long> {

    List<LoanPaymentScheduleEntity> findByLoanIdOrderByMonthNumberAsc(Long loanId);

    Optional<LoanPaymentScheduleEntity> findFirstByLoanIdAndStatusOrderByMonthNumberAsc(Long loanId, String status);
}