package com.example.demo.dto;

import lombok.ToString;

@ToString(callSuper = true, includeFieldNames = true)
public class BookGetDto extends BookBaseGetDto {
    private final CategoryBaseGetDto category;

    public BookGetDto(long id, String name, CategoryBaseGetDto category) {
        super(id, name);
        this.category = category;
    }

    public CategoryBaseGetDto getCategory() {
        return category;
    }

}
