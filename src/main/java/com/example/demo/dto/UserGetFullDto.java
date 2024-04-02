package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserGetFullDto extends UserGetBaseDto {
    private final String password;
    public UserGetFullDto(long id, String firstName, String lastName, String email, String password, boolean activated) {
        super(id, firstName, lastName, email, activated);
        this.password = password;
    }
}
