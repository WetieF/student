package com.wetie.student.services.exception;

public class TechnicalException extends RuntimeException {

    public static final String NOT_FOUND_TYP = "NOT_FOUND_TYP";
    public static final String CASHIER_ALREADY_EXISTS = "CASHIER_ALREADY_EXISTS";

    private final String typ;
    private final String message;

    public TechnicalException(String typ, String message) {
        super(message);
        this.typ = typ;
        this.message = typ + ", " + message;
    }
}




















































