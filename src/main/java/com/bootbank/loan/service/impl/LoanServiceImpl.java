package com.bootbank.loan.service.impl;

import com.bootbank.loan.exceptions.exception.InvalidRequestException;
import com.bootbank.loan.exceptions.exception.RecordNotFoundException;
import com.bootbank.loan.mapper.LoanMapper;
import com.bootbank.loan.model.dto.LoanApplyRequestDto;
import com.bootbank.loan.model.dto.LoanDto;
import com.bootbank.loan.model.dto.LoanPayRequestDto;
import com.bootbank.loan.model.dto.LoanPaymentScheduleResponseDto;
import com.bootbank.loan.model.dto.LoanResponseDto;
import com.bootbank.loan.model.entity.LoanEntity;
import com.bootbank.loan.model.entity.LoanPaymentScheduleEntity;
import com.bootbank.loan.model.enums.Currency;
import com.bootbank.loan.model.enums.Status;
import com.bootbank.loan.model.enums.Type;
import com.bootbank.loan.repository.LoanPaymentScheduleRepository;
import com.bootbank.loan.repository.LoanRepository;
import com.bootbank.loan.service.LoanService;
import com.bootbank.loan.util.LoanCalculator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final LoanPaymentScheduleRepository loanPaymentScheduleRepository;

    public LoanServiceImpl(LoanRepository loanRepository,
                           LoanPaymentScheduleRepository loanPaymentScheduleRepository) {
        this.loanRepository = loanRepository;
        this.loanPaymentScheduleRepository = loanPaymentScheduleRepository;
    }

    public LoanResponseDto getCustomerLoans(String cif) {
        List<LoanEntity> loans = loanRepository.findByCif(cif);

        if (loans.isEmpty()) {
            throw new RecordNotFoundException("Loan not found for Customer ID: " + cif);
        }

        var loanDtos = loans.stream().map(LoanMapper::mapEntityToResponse).toList();
        return LoanResponseDto.builder()
                .loans(loanDtos)
                .build();
    }

    @Override
    @Transactional
    public LoanDto applyLoan(String cif, LoanApplyRequestDto request) {

        Type type;
        try {
            type = Type.valueOf(request.loanType().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidRequestException("Kredit növü düzgün deyil: " + request.loanType());
        }

        BigDecimal annualRate = type.getAnnualRate();
        BigDecimal monthlyRate = LoanCalculator.calculateMonthlyRate(annualRate);
        BigDecimal monthlyPayment = LoanCalculator.calculateMonthlyPayment(
                request.amount(), monthlyRate, request.term());

        LocalDate today = LocalDate.now();

        LoanEntity loan = LoanEntity.builder()
                .cif(cif)
                .name(type.getDisplayName())
                .type(type)
                .amount(request.amount())
                .rate(annualRate)
                .monthlyPayment(monthlyPayment)
                .currency(Currency.AZN)
                .startDate(today)
                .endDate(today.plusMonths(request.term()))
                .status(Status.ACTIVE)
                .remaining(request.amount())
                .totalPaid(BigDecimal.ZERO)
                .paymentsLeft(request.term())
                .nextPaymentDate(today.plusMonths(1))
                .build();

        LoanEntity savedLoan = loanRepository.save(loan);

        List<LoanPaymentScheduleEntity> schedule = buildPaymentSchedule(
                savedLoan.getId(), request.amount(), monthlyRate, monthlyPayment, request.term(), today);
        loanPaymentScheduleRepository.saveAll(schedule);

        return LoanMapper.mapEntityToResponse(savedLoan);
    }

    @Override
    public LoanPaymentScheduleResponseDto getPaymentSchedule(String cif, Long loanId) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RecordNotFoundException("Loan not found: " + loanId));

        if (!cif.equals(loan.getCif())) {
            throw new RecordNotFoundException("Loan not found for Customer ID: " + cif);
        }

        List<LoanPaymentScheduleEntity> scheduleEntities =
                loanPaymentScheduleRepository.findByLoanIdOrderByMonthNumberAsc(loanId);

        var schedule = scheduleEntities.stream()
                .map(LoanMapper::mapScheduleEntityToResponse)
                .toList();

        return LoanPaymentScheduleResponseDto.builder()
                .id(loan.getId())
                .name(loan.getName())
                .type(loan.getType())
                .amount(loan.getAmount())
                .rate(loan.getRate())
                .term((int) ChronoUnit.MONTHS.between(loan.getStartDate(), loan.getEndDate()))
                .monthlyPayment(loan.getMonthlyPayment())
                .currency(loan.getCurrency())
                .schedule(schedule)
                .build();
    }

    @Override
    @Transactional
    public LoanDto payLoan(Long id, String cif, LoanPayRequestDto payRequest) {
        BigDecimal amount = payRequest.getAmount();

        // 1. Validasiya
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("Ödəniş məbləği 0-dan böyük olmalıdır.");
        }

        // 2. Kreditin tapılması və CIF yoxlanışı
        LoanEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Kredit məlumatı tapılmadı və ya bu müştəriyə aid deyil."));

        if (!loan.getCif().equals(cif)) {
            throw new RecordNotFoundException("Kredit məlumatı tapılmadı və ya bu müştəriyə aid deyil.");
        }

        if (Status.PAID.equals(loan.getStatus())) {
            throw new InvalidRequestException("Kredit artıq tamamilə ödənilib.");
        }

        // 3. Borcun yenilənməsi
        BigDecimal newRemaining = loan.getRemaining().subtract(amount);

        if (newRemaining.compareTo(BigDecimal.ZERO) <= 0) {
            loan.setStatus(Status.PAID);
            loan.setRemaining(BigDecimal.ZERO);
            loan.setPaymentsLeft(0);
            loan.setNextPaymentDate(null);
        } else {
            loan.setRemaining(newRemaining);
            loan.setPaymentsLeft(Math.max(0, loan.getPaymentsLeft() - 1));
            if (loan.getNextPaymentDate() != null) {
                loan.setNextPaymentDate(loan.getNextPaymentDate().plusMonths(1));
            }
        }

        BigDecimal totalPaid = loan.getTotalPaid() != null ? loan.getTotalPaid() : BigDecimal.ZERO;
        loan.setTotalPaid(totalPaid.add(amount));

        loanRepository.save(loan);

        // 4. Amortizasiya cədvəlinin yenilənməsi
        loanPaymentScheduleRepository.findFirstByLoanIdAndStatusOrderByMonthNumberAsc(id, "UNPAID")
                .ifPresent(schedule -> {
                    schedule.setStatus("PAID");
                    loanPaymentScheduleRepository.save(schedule);
                });

        return LoanMapper.mapEntityToResponse(loan);
    }

    private List<LoanPaymentScheduleEntity> buildPaymentSchedule(Long loanId,
                                                                 BigDecimal principal,
                                                                 BigDecimal monthlyRate,
                                                                 BigDecimal monthlyPayment,
                                                                 int termMonths,
                                                                 LocalDate startDate) {

        List<LoanPaymentScheduleEntity> schedule = new ArrayList<>();
        BigDecimal remainingBalance = principal;

        for (int month = 1; month <= termMonths; month++) {
            BigDecimal interestAmount = LoanCalculator.calculateInterestForMonth(remainingBalance, monthlyRate);
            BigDecimal principalAmount = monthlyPayment.subtract(interestAmount);
            remainingBalance = LoanCalculator.subtractAndRound(remainingBalance, principalAmount);

            LoanPaymentScheduleEntity row = LoanPaymentScheduleEntity.builder()
                    .loanId(loanId)
                    .monthNumber(month)
                    .paymentDate(startDate.plusMonths(month))
                    .monthlyPayment(monthlyPayment)
                    .principalAmount(principalAmount)
                    .interestAmount(interestAmount)
                    .remainingBalance(remainingBalance)
                    .status("UNPAID")
                    .build();

            schedule.add(row);
        }

        return schedule;
    }
}