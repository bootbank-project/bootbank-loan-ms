package com.bootbank.template.exceptions.response;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime time, int status, String error, String errorMessage) {
}
