package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class PrivilegeNotFoundException extends RestApiException {

    public PrivilegeNotFoundException(String s, int errorCode) {
        super(s, errorCode);
    }

    public PrivilegeNotFoundException(String s, int errorCode, Throwable throwable) {
        super(s, errorCode, throwable);
    }
}
