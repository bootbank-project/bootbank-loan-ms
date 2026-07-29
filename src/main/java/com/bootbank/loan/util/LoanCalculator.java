package com.bootbank.loan.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public final class LoanCalculator {

    private static final int SCALE = 2;
    private static final MathContext MATH_CONTEXT = new MathContext(20);

    private LoanCalculator() {
    }

    public static BigDecimal calculateMonthlyRate(BigDecimal annualRatePercent) {
        return annualRatePercent.divide(BigDecimal.valueOf(1200), MATH_CONTEXT);
    }

    public static BigDecimal calculateMonthlyPayment(BigDecimal principal, BigDecimal monthlyRate, int termMonths) {
        BigDecimal onePlusR = BigDecimal.ONE.add(monthlyRate);
        BigDecimal onePlusRPowN = onePlusR.pow(termMonths, MATH_CONTEXT);

        BigDecimal numerator = principal.multiply(monthlyRate).multiply(onePlusRPowN);
        BigDecimal denominator = onePlusRPowN.subtract(BigDecimal.ONE);

        return numerator.divide(denominator, SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateInterestForMonth(BigDecimal remainingBalance, BigDecimal monthlyRate) {
        return remainingBalance.multiply(monthlyRate).setScale(SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal subtractAndRound(BigDecimal balance, BigDecimal amountToSubtract) {
        return balance.subtract(amountToSubtract).setScale(SCALE, RoundingMode.HALF_UP);
    }
}