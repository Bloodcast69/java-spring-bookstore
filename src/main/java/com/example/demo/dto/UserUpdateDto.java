package com.example.demo.dto;

import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateDto extends UserCreateDto {
    @Positive(message = "User id has to be greater than 0")
    private final long id;

    public UserUpdateDto(long id, String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
        this.id = id;
    }
}
