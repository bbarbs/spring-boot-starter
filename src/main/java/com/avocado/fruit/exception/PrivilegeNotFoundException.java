package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class PrivilegeNotFoundException extends RestApiException {

    public PrivilegeNotFoundException(String s) {
        super(s);
    }

    public PrivilegeNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
