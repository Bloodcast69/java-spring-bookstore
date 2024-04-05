package com.example.demo.repository;

import com.example.demo.constants.MailType;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(exclude = "user")
@Table(name = "mail")
@Getter
@Setter
@ToString
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String subject;
    @Column(nullable = false)
    private String body;
    @Column(nullable = false)
    private MailType emailType;

    protected Mail() {

    }

    public Mail(User user, String subject, String body, MailType emailType) {
        this.user = user;
        this.subject = subject;
        this.body = body;
        this.emailType = emailType;
    }

}
