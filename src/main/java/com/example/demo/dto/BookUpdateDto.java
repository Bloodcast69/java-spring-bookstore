package com.example.demo.dto;

import lombok.ToString;

@ToString
public class BookUpdateDto {
    private final long id;
    private final String name;
    private final long categoryId;

    public BookUpdateDto(long id, String name, long categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }
}
