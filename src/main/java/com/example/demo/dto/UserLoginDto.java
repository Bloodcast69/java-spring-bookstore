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
public class UserLoginDto {
    @NotBlank(message = "Email name cannot be whitespace")
    @Email(message = "Wrong email format")
    private final String email;

    @ToString.Exclude
    @NotBlank(message = "Password name cannot be whitespace")
    @Size(min = 8, max = 64, message = "Password should be between 8-64 characters")
    private final String password;

    public UserLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
