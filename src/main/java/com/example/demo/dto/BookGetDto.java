package com.example.demo.dto;

public class BookGetDto {
    private long id;
    private String name;
    private CategoryGetDto category;

    public BookGetDto(long id, String name, CategoryGetDto category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryGetDto getCategory() {
        return category;
    }

}
