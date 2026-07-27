package com.bootbank.loan.repository;

import com.bootbank.loan.model.entity.LoanPaymentScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPaymentScheduleRepository extends JpaRepository<LoanPaymentScheduleEntity, Long> {

    List<LoanPaymentScheduleEntity> findByLoanIdOrderByMonthNumberAsc(Long loanId);
}