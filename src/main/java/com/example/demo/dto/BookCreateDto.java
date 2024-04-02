package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BookCreateDto {
    @NotBlank(message = "Name cannot be null or only whitespace")
    @Size(min = 2, max = 256, message = "Book name should be between 2-256 characters")
    private final String name;

    @NotNull(message = "Category id cannot be null")
    @Positive(message = "Category id has to be greater than 0")
    private final long categoryId;

    public BookCreateDto(String name, long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }
}
