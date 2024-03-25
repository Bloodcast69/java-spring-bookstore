package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CategoryCreateDto {
    @NotNull
    @Length(min = 2, max = 256, message = "Category name should be between 2-256 characters")
    private final String name;

    @JsonCreator
    public CategoryCreateDto(@JsonProperty String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
