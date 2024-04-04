package com.example.demo.repository;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(exclude = "mails")
@Table(name = "users")
@ToString
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @Column(nullable = false)
    private boolean activated;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    List<Mail> mails = new ArrayList<>();

    protected User() {

    }

    public User(String firstName, String lastName, String email, String password, boolean activated) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.activated = activated;
    }
}
