package com.bootbank.loan.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    ACTIVE,
    PAID,
    OVERDUE;

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}