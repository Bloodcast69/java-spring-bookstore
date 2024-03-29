package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class CategoryGetDto extends CategoryBaseGetDto {
    private List<BookBaseGetDto> books = null;

    public CategoryGetDto(long id, String name, List<BookBaseGetDto> books) {
        super(id, name);
        if (books != null) {
            this.books = books;
        }
    }
}
