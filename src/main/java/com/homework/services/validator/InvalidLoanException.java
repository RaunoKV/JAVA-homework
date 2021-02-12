package com.homework.services.validator;

public class InvalidLoanException extends Exception{

    private static final long serialVersionUID = -3413056016170496407L;

    public InvalidLoanException(String message) {
        super(message);
    }
    
}
