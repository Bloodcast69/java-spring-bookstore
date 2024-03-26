package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public class CategoryDeleteDto {
    @NotNull
    private boolean force;

    @JsonCreator
    public CategoryDeleteDto(@JsonProperty  boolean force) {
        this.force = force;
    }

    public boolean getForce() {
        return force;
    }
}
