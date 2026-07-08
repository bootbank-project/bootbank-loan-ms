package com.bootbank.template.exceptions.handler;

import com.bootbank.template.exceptions.exception.RecordNotFoundException;
import com.bootbank.template.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFoundException(RecordNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                exception.getLocalizedMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
