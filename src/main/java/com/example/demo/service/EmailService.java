package com.example.demo.service;

import com.example.demo.dto.EmailDetails;
import com.example.demo.repository.Mail;

public interface EmailService {
    void sendSimpleMail(Mail mail);


    void sendAccountCreatedEmail(Mail mail);

    void sendAccountConfirmedEmail(Mail mail);

    void sendAccountBlockedEmail(Mail mail);

    void sendUserPasswordResetInitEmail(Mail mail);

    void sendUserPasswordResetEmail(Mail mail);

    EmailDetails prepareCreateAccountConfirmEmailToSend(String recipient);

    EmailDetails prepareAccountConfirmationEmailToSend(String recipient);

    EmailDetails prepareAccountBlockedEmailToSend(String recipient);

    EmailDetails prepareUserPasswordResetInitEmailToSend(String recipient);

    EmailDetails prepareUserPasswordResetEmailToSend(String recipient);
}
