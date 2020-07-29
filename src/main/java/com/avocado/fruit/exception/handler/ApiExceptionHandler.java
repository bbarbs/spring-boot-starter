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
public class ApiExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> employeeNotFoundException(RestApiException e) {
        return new ResponseEntity<>(new RestExceptionMessage(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<RestExceptionMessage> emailExistsException(RestApiException e) {
        return new ResponseEntity<>(new RestExceptionMessage(
                new Date(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> emailNotFoundException(RestApiException e) {
        return new ResponseEntity<>(new RestExceptionMessage(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PrivilegeNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> privilegeNotFoundException(RestApiException e) {
        return new ResponseEntity<>(new RestExceptionMessage(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> roleNotFoundException(RestApiException e) {
        return new ResponseEntity<>(new RestExceptionMessage(
                new Date(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND,
                new ErrorMessage(e.getMessage())
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InconsistentIDException.class)
    public RestExceptionMessage inconsistentIDException(RestApiException e) {
        return RestExceptionMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .error(new ErrorMessage(e.getMessage()))
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public RestExceptionMessage expiredJwtException(RestApiException e) {
        return RestExceptionMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .error(new ErrorMessage(e.getMessage()))
                .build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    public RestExceptionMessage invalidTokenException(RestApiException e) {
        return RestExceptionMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .error(new ErrorMessage(e.getMessage()))
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    public RestExceptionMessage badRequestException(RestApiException e) {
        return RestExceptionMessage.builder()
                .timestamp(new Date())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .error(new ErrorMessage(e.getMessage()))
                .build();
    }

}
