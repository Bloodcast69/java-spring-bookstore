package com.example.demo.dto;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserActivateDto {
    @Positive(message = "User id has to be greater than 0")
    private final long id;

    @NotNull(message = "Activated cannot be null")
    @AssertTrue(message = "Activated has to be true")
    private final boolean activated;

    public UserActivateDto(long id, boolean activated) {
        this.id = id;
        this.activated = activated;
    }
}
