package com.avocado.fruit.exception;

import com.avocado.fruit.exception.config.RestApiException;

public class RoleNotFoundException extends RestApiException {

    public RoleNotFoundException(String s) {
        super(s);
    }

    public RoleNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
