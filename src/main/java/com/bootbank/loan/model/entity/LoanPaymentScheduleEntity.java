package com.bootbank.loan.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LOAN_PAYMENT_SCHEDULE")
public class LoanPaymentScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_payment_schedule_seq")
    @SequenceGenerator(name = "loan_payment_schedule_seq", sequenceName = "LOAN_PAYMENT_SCHEDULE_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "LOAN_ID")
    private Long loanId;

    @Column(name = "MONTH_NUMBER")
    private Integer monthNumber;

    @Column(name = "PAYMENT_DATE")
    private LocalDate paymentDate;

    @Column(name = "MONTHLY_PAYMENT")
    private BigDecimal monthlyPayment;

    @Column(name = "PRINCIPAL_AMOUNT")
    private BigDecimal principalAmount;

    @Column(name = "INTEREST_AMOUNT")
    private BigDecimal interestAmount;

    @Column(name = "REMAINING_BALANCE")
    private BigDecimal remainingBalance;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PAID_DATE")
    private LocalDateTime paidDate;
}