package com.example.demo.repository;

import com.example.demo.dto.BookBaseGetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
