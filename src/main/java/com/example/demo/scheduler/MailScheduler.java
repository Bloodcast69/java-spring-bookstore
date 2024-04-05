package com.example.demo.scheduler;

import com.example.demo.repository.Mail;
import com.example.demo.repository.MailRepository;
import com.example.demo.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailScheduler {
    private final Logger logger = LoggerFactory.getLogger(MailScheduler.class);
    private final MailRepository mailRepository;
    private final EmailService emailService;

    public MailScheduler(MailRepository mailRepository, EmailService emailService) {
        this.mailRepository = mailRepository;
        this.emailService = emailService;

    }

    @Scheduled(fixedRateString = "${scheduler.mailer.interval}")
    public void sendEmails() {
        List<Mail> mailsToSend = mailRepository.findAll();

        if (mailsToSend.isEmpty()) {
            logger.info("sendEmails No new mails to send.");
            return;
        }

        mailsToSend.forEach(mail -> {
            logger.info("sendEmails sending email to recipient = {}", mail.getUser().getEmail());

            switch (mail.getEmailType()) {
                case ACCOUNT_CREATED -> {
                    emailService.sendAccountCreatedEmail(mail);
                    break;
                }
                case ACCOUNT_CONFIRMED -> {
                    emailService.sendAccountConfirmedEmail(mail);
                    break;
                }
                case ACCOUNT_BLOCKED_BY_LOGIN_LIMIT -> {
                    emailService.sendAccountBlockedEmail(mail);
                    break;
                }
                case PASSWORD_RESET_INIT -> {
                    emailService.sendUserPasswordResetInitEmail(mail);
                    break;
                }
                case PASSWORD_RESET_SUCCESS -> {
                    emailService.sendUserPasswordResetEmail(mail);
                    break;
                }
                default -> logger.info("sendEmails no emails for specified types");
            }
        });
    }
}
