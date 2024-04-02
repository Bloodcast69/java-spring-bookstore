package com.example.demo.exception;

import com.example.demo.dto.ExceptionResponseDto;
import com.example.demo.dto.ValidationExceptionResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.ToString;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;


@ControllerAdvice
@ToString
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(
                new ExceptionResponseDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handlePersistenceException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({AuthenticationServiceException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleAuthenticationServiceException(AuthenticationServiceException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ValidationExceptionResponseDto> errors = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ValidationExceptionResponseDto(
                        e.getField(),
                        e.getDefaultMessage()))
                .toList();

        return new ResponseEntity<>(
                errors,
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST
        );
    }
}
