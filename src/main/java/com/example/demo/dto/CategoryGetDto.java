package com.example.demo.dto;

import java.util.List;

public class CategoryGetDto {
    private long id;
    private String name;
//    private List<BookGetDto> books;

    public CategoryGetDto(long id, String name) {
        this.id = id;
        this.name = name;
//        this.books = books;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public List<BookGetDto> getBooks() {
//        return books;
//    }
}
