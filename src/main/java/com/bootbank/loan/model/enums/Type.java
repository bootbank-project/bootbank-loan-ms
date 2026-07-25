package com.bootbank.loan.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.math.BigDecimal;

public enum Type {

    CONSUMER("İstehlak Krediti", new BigDecimal("14.0")),
    MORTGAGE("İpoteka Krediti", new BigDecimal("8.0")),
    AUTO("Avto Kredit", new BigDecimal("12.0")),
    BUSINESS("Biznes Krediti", new BigDecimal("10.0"));

    private final String displayName;
    private final BigDecimal annualRate;

    Type(String displayName, BigDecimal annualRate) {
        this.displayName = displayName;
        this.annualRate = annualRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BigDecimal getAnnualRate() {
        return annualRate;
    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}