package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

@ToString
public class CategoryUpdateDto extends CategoryCreateDto {

    @JsonCreator
    public CategoryUpdateDto(@JsonProperty String name) {
        super(name);
    }
}
