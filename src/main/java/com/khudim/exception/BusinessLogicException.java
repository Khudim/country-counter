package com.khudim.exception;

public class BusinessLogicException extends RuntimeException {

    private BusinessLogicException(String message) {
        super(message);
    }

    public static BusinessLogicException countryCodeNotSupported(String countryCode) {
        return new BusinessLogicException("Country code = " + countryCode + ", not supported.");
    }
}
