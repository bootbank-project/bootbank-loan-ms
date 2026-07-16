package com.bootbank.loan.model.entity;

import com.bootbank.loan.model.enums.Currency;
import com.bootbank.loan.model.enums.Status;
import com.bootbank.loan.model.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "LOAN")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_seq")
    @SequenceGenerator(name = "loan_seq", sequenceName = "LOAN_SEQ", allocationSize = 1)
    private Long id;
    @Column(name = "CIF")
    private String cif;
    @Column(name = "NAME")
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private Type type;
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Column(name = "RATE")
    private BigDecimal rate;
    @Column(name = "MONTHLY_PAYMENT")
    private BigDecimal monthlyPayment;
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY")
    private Currency currency;
    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;
    @Column(name = "REMAINING")
    private BigDecimal remaining;
    @Column(name = "TOTAL_PAID")
    private BigDecimal totalPaid;
    @Column(name = "PAYMENTS_LEFT")
    private Integer paymentsLeft;
    @Column(name = "NEXT_PAYMENT_DATE")
    private LocalDate nextPaymentDate;

}

