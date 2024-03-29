package com.example.demo.exception;

import com.example.demo.dto.ExceptionResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(Exception ex) {
        return new ResponseEntity<>(
                new ExceptionResponseDto(ex.getMessage()),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handlePersistenceException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(
                new ExceptionResponseDto(ex.getLocalizedMessage()),
                new HttpHeaders(),
                HttpStatus.CONFLICT
        );
    }
}
