package com.example.demo.dto;

public class BookBaseGetDto {
    private final long id;
    private final String name;

    public BookBaseGetDto(long id, String name) {
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
