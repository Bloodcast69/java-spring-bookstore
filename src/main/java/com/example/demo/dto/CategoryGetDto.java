package com.example.demo.dto;

import lombok.ToString;

import java.util.List;

@ToString
public class CategoryGetDto extends CategoryBaseGetDto {
    private List<BookBaseGetDto> books = null;

    public CategoryGetDto(long id, String name, List<BookBaseGetDto> books) {
        super(id, name);
        if (books != null) {
            this.books = books;
        }
    }

    public List<BookBaseGetDto> getBooks() {
        return books;
    }
}
