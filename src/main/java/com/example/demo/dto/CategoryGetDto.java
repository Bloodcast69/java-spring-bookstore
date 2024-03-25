package com.example.demo.dto;

import java.util.List;

public class CategoryGetDto extends CategoryBaseGetDto {
    private final List<BookBaseGetDto> books;

    public CategoryGetDto(long id, String name, List<BookBaseGetDto> books) {
        super(id, name);
        this.books = books;
    }

    public List<BookBaseGetDto> getBooks() {
        return books;
    }
}
