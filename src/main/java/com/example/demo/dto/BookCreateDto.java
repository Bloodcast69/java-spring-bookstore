package com.example.demo.dto;

import lombok.ToString;

@ToString
public class BookCreateDto {
    private final String name;
    private final long categoryId;

    public BookCreateDto(String name, long categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
