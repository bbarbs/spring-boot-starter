package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class EmployeeNotFoundException extends RestApiException {

    public EmployeeNotFoundException(String s) {
        super(s);
    }

    public EmployeeNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
