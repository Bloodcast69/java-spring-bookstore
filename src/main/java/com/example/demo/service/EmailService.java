package com.example.demo.service;

import com.example.demo.dto.EmailDetails;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendSimpleMail(EmailDetails details);

    void sendMessageWithAttachment(EmailDetails details) throws MessagingException;

    EmailDetails prepareRegisterConfirmationEmailToSend(String recipient);
}
