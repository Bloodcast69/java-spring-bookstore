package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailAttachment {
    private final String name;
    private final String path;

    public EmailAttachment(String name, String path) {
        this.name = name;
        this.path = path;
    }
}
