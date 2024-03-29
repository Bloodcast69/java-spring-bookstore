package com.example.demo.repository;

import com.example.demo.dto.BookBaseGetDto;
import jakarta.persistence.*;

@NamedNativeQuery(name = "Book.findForCategory",
        query = "SELECT b.id as id, b.name as name FROM book b WHERE b.category_id = :id",
        resultSetMapping = "Mapping.BookBaseGetDto")
@SqlResultSetMapping(name = "Mapping.BookBaseGetDto",
        classes = @ConstructorResult(targetClass = BookBaseGetDto.class,
                columns = {
                        @ColumnResult(name = "id"),
                        @ColumnResult(name = "name"),
                }))
@Entity
@Table(name = "book")
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

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void clearCategory() {
        if (this.category == null) {
            return;
        }

        this.category.getBooks().remove(this);
        this.category = null;
    }

    public boolean categoryIsEmpty() {
        return this.category == null;
    }

    public void setName(String name) {
        this.name = name;
    }
}
