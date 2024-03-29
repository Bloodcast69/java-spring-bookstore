package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CategoryUpdateDto extends CategoryCreateDto {

    @JsonCreator
    public CategoryUpdateDto(@JsonProperty String name) {
        super(name);
    }
}
