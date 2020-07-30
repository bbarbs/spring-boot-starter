package com.avocado.fruit.exception.handler;


import com.avocado.fruit.exception.*;
import com.avocado.fruit.exception.config.ErrorMessage;
import com.avocado.fruit.exception.config.RestApiException;
import com.avocado.fruit.exception.config.RestExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class RestExceptionHandler extends AbstractExceptionResponse {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> employeeNotFoundException(RestApiException e) {
        return errorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<RestExceptionMessage> emailExistsException(RestApiException e) {
        return errorResponse(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> emailNotFoundException(RestApiException e) {
        return errorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrivilegeNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> privilegeNotFoundException(RestApiException e) {
        return errorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> roleNotFoundException(RestApiException e) {
        return errorResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InconsistentIDException.class)
    public ResponseEntity<RestExceptionMessage> inconsistentIDException(RestApiException e) {
        return errorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<RestExceptionMessage> expiredJwtException(RestApiException e) {
        return errorResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<RestExceptionMessage> invalidTokenException(RestApiException e) {
        return errorResponse(e, HttpStatus.UNAUTHORIZED);
    }
}
