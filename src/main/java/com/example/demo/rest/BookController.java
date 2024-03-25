package com.example.demo.rest;

import com.example.demo.dto.BookGetDto;
import com.example.demo.mapper.BookMapper;
import com.example.demo.repository.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class BookController {
    private final BookRepository repository;
    private final BookMapper mapper;

    public BookController(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookGetDto> getBookById(@PathVariable(name = "id") long id) {
        Optional<Book> entity = repository.findById(id);

        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BookGetDto response = mapper.bookToBookGetDto(entity.get());

        return ResponseEntity.ok(response);
    }
}
