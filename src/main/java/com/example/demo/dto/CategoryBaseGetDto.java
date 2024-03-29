package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CategoryBaseGetDto {
    private final long id;
    private final String name;

    public CategoryBaseGetDto(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
