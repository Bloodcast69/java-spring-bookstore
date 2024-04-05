package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPasswordResetInit {
//    @NotBlank(message = "Email name cannot be whitespace")
//    @Email(message = "Wrong email format")
    private final String email;

    public UserPasswordResetInit(String email) {
        this.email = email;
    }
}
