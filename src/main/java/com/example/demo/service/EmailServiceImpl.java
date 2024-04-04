package com.example.demo.service;

import com.example.demo.dto.EmailAttachment;
import com.example.demo.dto.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final String sender;

    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username") String sender) {
        this.javaMailSender = javaMailSender;
        this.sender = sender;
    }

    public void sendSimpleMail(EmailDetails details) {
        logger.info("Called sendSimpleMail with details = {}", details);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(details.getRecipient());
        mail.setSubject(details.getSubject());
        mail.setText(details.getBody());

        javaMailSender.send(mail);
        logger.info("sendSimpleMail email sent with details = {}", details);
    }

    public void sendMessageWithAttachment(EmailDetails details) throws MessagingException {
        logger.info("Called sendMessageWithAttachment with details = {}", details);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(sender);
        helper.setTo(details.getRecipient());
        helper.setSubject(details.getSubject());
        helper.setText(details.getBody());

        details.getAttachments().forEach(attachment -> {
            try {
                helper.addAttachment(attachment.getName(), new FileSystemResource(attachment.getPath() + "/" + attachment.getName()));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });

        logger.info("sendMessageWithAttachment email sent with details = {}", details);
        javaMailSender.send(message);
    }

    public EmailDetails prepareRegisterConfirmationEmailToSend(String recipient) {
//        ArrayList<EmailAttachment> attachments = new ArrayList<>();
//        attachments.add(new EmailAttachment("invoice.txt", "static_files"));
//        attachments.add(new EmailAttachment("invoice2.txt", "static_files"));
        return new EmailDetails(recipient, "Java BookStore - account created", "Your account is created but it's not active yet.");
    }
}
