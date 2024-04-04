package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmailDetails {
    private final String recipient;
    private final String body;
    private final String subject;
    private List<EmailAttachment> attachments;

    public EmailDetails(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.body = body;
        this.subject = subject;
    }

    public EmailDetails(String recipient, String subject, String body, List<EmailAttachment> attachments) {
        this.recipient = recipient;
        this.body = body;
        this.subject = subject;
        this.attachments = attachments;
    }
}
