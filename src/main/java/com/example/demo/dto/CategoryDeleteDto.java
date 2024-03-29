package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class CategoryDeleteDto {
    @NotNull(message = "Force cannot be null")
    private boolean force;

    @JsonCreator
    public CategoryDeleteDto(@JsonProperty  boolean force) {
        this.force = force;
    }
}
