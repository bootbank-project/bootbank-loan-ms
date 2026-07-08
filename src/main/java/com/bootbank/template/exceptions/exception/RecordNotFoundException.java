package com.bootbank.template.exceptions.exception;

public class LoanNotFoundException extends RuntimeException{

    public LoanNotFoundException(String message) {
        super(message);
    }
}
