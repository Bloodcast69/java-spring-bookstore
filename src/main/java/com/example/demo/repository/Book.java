package com.example.demo.repository;

import jakarta.persistence.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    protected Book() {

    }

    public Book(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void clearCategory() {
        this.category = null;
    }
}
