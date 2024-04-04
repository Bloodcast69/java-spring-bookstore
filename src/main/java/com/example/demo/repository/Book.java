package com.example.demo.repository;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@EqualsAndHashCode(exclude = "category")
@Table(name = "book")
@ToString
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private Category category;

    protected Book() {

    }

    public Book(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public boolean categoryIsEmpty() {
        return this.category == null;
    }

}
