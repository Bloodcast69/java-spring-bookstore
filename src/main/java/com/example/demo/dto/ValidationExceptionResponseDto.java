package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationExceptionResponseDto {
    private final String field;
    private final String message;

    public ValidationExceptionResponseDto(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
