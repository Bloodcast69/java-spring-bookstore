package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseDto {
    private final String message;
    public ExceptionResponseDto(String message) {
        this.message = message;
    }
}
