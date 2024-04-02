package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserGetBaseDto {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final boolean activated;

    public UserGetBaseDto(long id, String firstName, String lastName, String email, boolean activated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
    }
}
