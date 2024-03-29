package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public class BookUpdateDto extends BookCreateDto {
    @NotNull(message = "Id cannot be null")
    private final long id;

    public BookUpdateDto(long id, String name, long categoryId) {
        super(name, categoryId);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
