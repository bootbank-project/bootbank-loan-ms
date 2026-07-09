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
@Table(name = "loans", schema = "bootbank_loan_db")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cif;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;
    private BigDecimal amount;
    private Double rate;
    private BigDecimal monthlyPayment;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    private BigDecimal remaining;
    private BigDecimal totalPaid;
    private Integer paymentsLeft;
    private LocalDate nextPaymentDate;

}

