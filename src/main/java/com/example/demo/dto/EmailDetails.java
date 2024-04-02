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
    private final String msgBody;
    private final String subject;
    private List<EmailAttachment> attachments;

    public EmailDetails(String recipient, String msgBody, String subject) {
        this.recipient = recipient;
        this.msgBody = msgBody;
        this.subject = subject;
    }

    public EmailDetails(String recipient, String msgBody, String subject, List<EmailAttachment> attachments) {
        this.recipient = recipient;
        this.msgBody = msgBody;
        this.subject = subject;
        this.attachments = attachments;
    }
}
