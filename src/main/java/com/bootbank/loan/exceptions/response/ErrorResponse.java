package com.bootbank.loan.exceptions.response;

import java.time.Instant;

public record ErrorResponse(Instant time, int status, String error, String errorMessage) {
}
