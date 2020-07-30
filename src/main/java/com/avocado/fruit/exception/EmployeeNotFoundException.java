package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class EmployeeNotFoundException extends RestApiException {

    public EmployeeNotFoundException(String s, int errorCode) {
        super(s, errorCode);
    }

    public EmployeeNotFoundException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
