package com.example.demo.dto;

public class CategoryBaseGetDto {
    private final long id;
    private final String name;

    public CategoryBaseGetDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
