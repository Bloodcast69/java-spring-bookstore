package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CategoryCreateDto {
    @NotBlank(message = "Name cannot be null or only whitespace")
    @Size(min = 2, max = 256, message = "Category name should be between 2-256 characters")
    private final String name;

    @JsonCreator
    public CategoryCreateDto(@JsonProperty String name) {
        this.name = name;
    }
}
