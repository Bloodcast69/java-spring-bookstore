package com.example.demo.repository;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(exclude = "books")
@Table(name = "category")
@ToString
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Book> books = new ArrayList<>();

    protected Category() {

    }

    public Category(String name) {
        this.name = name;
    }

}
