package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class BookBaseGetDto {
    private final long id;
    private final String name;

    public BookBaseGetDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
