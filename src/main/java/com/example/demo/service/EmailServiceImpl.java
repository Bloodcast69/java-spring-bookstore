package com.example.demo.service;

import com.example.demo.dto.EmailDetails;
import com.example.demo.repository.Mail;
import com.example.demo.repository.MailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final String sender;
    private final MailRepository mailRepository;

    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username}") String sender, MailRepository mailRepository) {
        this.javaMailSender = javaMailSender;
        this.sender = sender;
        this.mailRepository = mailRepository;
    }

    public void sendSimpleMail(Mail mail) {
        logger.info("Called sendSimpleMail with details = {}", mail);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(mail.getUser().getEmail());
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getBody());

        javaMailSender.send(simpleMailMessage);
        logger.info("sendSimpleMail email sent with details = {}", mail);
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

    @Transactional
    public void sendAccountCreatedEmail(Mail mail) {
        logger.info("sendAccountCreatedEmail sending email to recipient = {}", mail.getUser().getEmail());

        sendAndRemoveEmail(mail);
    }

    @Transactional
    public void sendAccountConfirmedEmail(Mail mail) {
        logger.info("sendAccountConfirmedEmail sending email to recipient = {}", mail.getUser().getEmail());

        sendAndRemoveEmail(mail);
    }

    @Transactional
    public void sendAccountBlockedEmail(Mail mail) {
        logger.info("sendAccountBlockedEmail sending email to recipient = {}", mail.getUser().getEmail());

        sendAndRemoveEmail(mail);
    }

    public EmailDetails prepareCreateAccountConfirmEmailToSend(String recipient) {
        return new EmailDetails(recipient, "Java BookStore - account created", "Your account is created but it's not active yet.");
    }

    public EmailDetails prepareAccountConfirmationEmailToSend(String recipient) {
        return new EmailDetails(recipient, "Java BookStore - account confirmed", "Your account is confirmed and active.");
    }

    public EmailDetails prepareAccountBlockedEmailToSend(String recipient) {
        return new EmailDetails(recipient, "Java BookStore - account is blocked", "Your account is blocked due to exceed invalid login attempts. Please reset your password.");
    }

    private void sendAndRemoveEmail(Mail mail) {
        sendSimpleMail(mail);

        mailRepository.delete(mail);
    }
}
