package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BookUpdateDto extends BookCreateDto {
    @NotNull(message = "Id cannot be null")
    @Positive(message = "Id has to be greater than 0")
    private final long id;

    public BookUpdateDto(long id, String name, long categoryId) {
        super(name, categoryId);
        this.id = id;
    }
}
