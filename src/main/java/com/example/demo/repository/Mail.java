package com.example.demo.repository;

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

    protected Mail() {

    }

    public Mail(User user, String subject, String body) {
        this.user = user;
        this.subject = subject;
        this.body = body;
    }

}
