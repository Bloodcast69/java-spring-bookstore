package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserCreateDto {
    @NotBlank(message = "First name cannot be whitespace")
    @Size(min = 3, max = 32, message = "First name should be between 3-32 characters")
    private final String firstName;

    @NotBlank(message = "Last name cannot be whitespace")
    @Size(min = 3, max = 32, message = "Last name should be between 3-32 characters")
    private final String lastName;

    @NotBlank(message = "Email name cannot be whitespace")
    @Email(message = "Wrong email format")
    private final String email;

    @NotBlank(message = "Password name cannot be whitespace")
    @Size(min = 8, max = 64, message = "Password should be between 8-64 characters")
    private final String password;

    public UserCreateDto(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
